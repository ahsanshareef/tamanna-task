package com.tamanna.demo;

import com.tamanna.demo.controller.InterviewController;
import com.tamanna.demo.dto.SchedualDateTimeDTO;
import com.tamanna.demo.enums.Role;
import com.tamanna.demo.model.Timeslots;
import com.tamanna.demo.model.User;
import com.tamanna.demo.repository.TimeSlotRepository;
import com.tamanna.demo.request.InterviewerRequest;
import com.tamanna.demo.response.Response;
import com.tamanna.demo.response.TimeSlotsForCandidateResponse;
import com.tamanna.demo.service.ITimeSlotService;
import com.tamanna.demo.service.IUserService;
import com.tamanna.demo.service.impl.TimeSlotServiceImpl;
import com.tamanna.demo.utils.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.bind.ValidationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@InjectMocks
	TimeSlotServiceImpl timeSlotService;

	@InjectMocks
	InterviewController interviewController;

	@Mock
	TimeSlotRepository timeSlotRepository;

	@Mock
	ITimeSlotService iTimeSlotService;

	@Mock
	IUserService userService;

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	void prepareTimeSlotListExceptionTest() throws ParseException {
		List<SchedualDateTimeDTO> request = new ArrayList<>();
		SchedualDateTimeDTO object = new SchedualDateTimeDTO(formatter.parse("2022-10-18"), new ArrayList<String>(){{
			this.add("09:30:00 - 12:00:00");
			this.add("13:00:00 - 15:00:00");
		}});
		request.add(object);
		ValidationException validationException = Assertions.assertThrows(ValidationException.class,
				() -> Util.prepareTimeSlotList(request, new User(5L, "Ahsan", "Interviewer1", Role.INTERVIEWER)));
		Assertions.assertTrue(validationException.getMessage().contains("Selected time is not correct"));
	}

	@Test
	void prepareTimeSlotListTest() throws ParseException, ValidationException {
		List<SchedualDateTimeDTO> request = new ArrayList<>();
		SchedualDateTimeDTO object = new SchedualDateTimeDTO(formatter.parse("2022-10-18"), new ArrayList<String>(){{
			this.add("09:00:00 - 12:00:00");
			this.add("13:00:00 - 15:00:00");
		}});
		request.add(object);
		List<Timeslots> timeslots = Util.prepareTimeSlotList(request, new User(5L, "Ahsan", "Interviewer1", Role.INTERVIEWER));
		Assertions.assertNotNull(timeslots);
		Assertions.assertEquals(2, timeslots.size());
	}

	@Test
	public void getAvailableTimeSlotsForCandiDateTest() throws ParseException {
		String date_string = "2022-10-18";
		Date date = formatter.parse(date_string);
		User candidate = new User(4L, "Ahsan", "Candidate", Role.CANDIDATE);
		User interviewer1 = new User(5L, "Ahsan", "Interviewer1", Role.INTERVIEWER);
		User interviewer2 = new User(6L, "Ahsan", "Interviewer2", Role.INTERVIEWER);
		Mockito.when(timeSlotRepository.getTimeslotsByUser(Mockito.any())).thenReturn(new ArrayList<Timeslots>(){{
			this.add(new Timeslots(42L, date, LocalTime.of(9, 0, 0), LocalTime.of(18, 0, 0), candidate));
		}});
		Mockito.when(timeSlotRepository.getAvailableSlotsForCadidate(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(new ArrayList<Timeslots>(){{
			this.add(new Timeslots(43L, date, LocalTime.of(9, 0, 0), LocalTime.of(12, 0, 0), interviewer1));
			this.add(new Timeslots(44L, date, LocalTime.of(13, 0, 0), LocalTime.of(15, 0, 0), interviewer1));
			this.add(new Timeslots(45L, date, LocalTime.of(15, 0, 0), LocalTime.of(17, 0, 0), interviewer2));
		}});

		List<TimeSlotsForCandidateResponse> availableTimeSlotsForCandiDate = timeSlotService.getAvailableTimeSlotsForCandiDate(4L, date, date);
		Assertions.assertNotNull(availableTimeSlotsForCandiDate);
		Assertions.assertEquals(3, availableTimeSlotsForCandiDate.size());
	}

	@Test
	void getCandidateSchedualTest() throws ParseException {
		String date_string = "2022-10-18";
		Date date = formatter.parse(date_string);
		Mockito.when(userService.get(Mockito.any())).thenReturn(new User(4L, "Ahsan", "Candidate", Role.CANDIDATE));
		Mockito.when(iTimeSlotService.getAvailableTimeSlotsForCandiDate(4L, date, date)).thenReturn(new ArrayList<>(){{
			this.add(new TimeSlotsForCandidateResponse("Ahsan Interviewer1", "2022-10-18","09:00 - 10:00"));
			this.add(new TimeSlotsForCandidateResponse("Ahsan Interviewer1", "2022-10-18","13:00 - 14:00"));
			this.add(new TimeSlotsForCandidateResponse("Ahsan Interviewer2", "2022-10-18","15:00 - 16:00"));
		}});
		Response result = interviewController.getCandidateSchedual(4L, date, date);
		Assertions.assertNotNull(result.getPayload());
		Assertions.assertEquals(200, result.getStatus());
		Assertions.assertEquals(3, ((List<TimeSlotsForCandidateResponse>) result.getPayload()).size());
	}

	@Test
	void addUserSchedualTest() throws ParseException {
		User user = new User(5L, "Ahsan", "Candidate", Role.INTERVIEWER);
		Mockito.when(userService.get(Mockito.any())).thenReturn(user);
		InterviewerRequest request = new InterviewerRequest();
		request.setId(5L);
		request.setRole(Role.INTERVIEWER);
		request.setAvailability(new ArrayList<>(){{
			this.add(new SchedualDateTimeDTO(formatter.parse("2022-10-18"), new ArrayList<String>(){{
				this.add("09:00:00 - 12:00:00");
				this.add("13:00:00 - 15:00:00");
			}}));
		}});
		Mockito.when(iTimeSlotService.addAll(Mockito.anyList())).thenReturn(new ArrayList<>(){{
			this.add(new Timeslots(43L, formatter.parse("2022-10-18"), LocalTime.of(9, 0, 0), LocalTime.of(12, 0, 0), user));
			this.add(new Timeslots(44L, formatter.parse("2022-10-18"), LocalTime.of(13, 0, 0), LocalTime.of(15, 0, 0), user));
		}});

		Response response = interviewController.addUserSchedual(request);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(200, response.getStatus());
		Assertions.assertEquals(2, ((List<TimeSlotsForCandidateResponse>) response.getPayload()).size());
	}

	@Test
	void addUserSchedualErrorTest() throws ParseException {
		User user = new User(5L, "Ahsan", "Candidate", Role.INTERVIEWER);
		Mockito.when(userService.get(Mockito.any())).thenReturn(user);
		InterviewerRequest request = new InterviewerRequest();
		request.setId(5L);
		request.setRole(Role.INTERVIEWER);
		request.setAvailability(new ArrayList<>(){{
			this.add(new SchedualDateTimeDTO(formatter.parse("2022-10-18"), new ArrayList<String>(){{
				this.add("09:30:00 - 12:00:00");
				this.add("13:00:00 - 15:00:00");
			}}));
		}});

		Response response = interviewController.addUserSchedual(request);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(500, response.getStatus());
		Assertions.assertNull(response.getPayload());
	}

}
