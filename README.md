# tamanna-task

API # 1:
URL: http://localhost:8080/tamannam/schedual/{candidateId}/{startDate}/{endDate}
Method: GET
Sample request: http://localhost:8080/tamannam/schedual/4/2022-10-18/2022-10-18
Response:
{
    "status": 200,
    "message": "Available time slots",
    "payload": [
        {
            "interviewerName": "ahsan sharif",
            "date": "2022-10-18 00:00:00.0",
            "timeSlots": "09:00 - 10:00"
        },
        {
            "interviewerName": "ahsan sharif",
            "date": "2022-10-18 00:00:00.0",
            "timeSlots": "13:00 - 14:00"
        },
        {
            "interviewerName": "Inter 2 sharif",
            "date": "2022-10-18 00:00:00.0",
            "timeSlots": "15:00 - 16:00"
        }
    ]
}

API # 2:
URL: http://localhost:8080/tamannam/schedual
Method: POST
Sample request: 
    URL: http://localhost:8080/tamannam/schedual
    Body:
        {
            "id": 4,
            "role": "CANDIDATE",
            "availability": [
                {
                    "availableDate": "2022-10-18",
                    "availableTime": [
                            "09:00:00 - 12:00:00",
                            "13:00:00 - 15:00:00"
                    ]
                }
            ]
        }
Response:
{
    "status": 200,
    "message": "Time slots saved successfully",
    "payload": [
        {
            "id": 54,
            "date": "2022-10-18T00:00:00.000+00:00",
            "startDateTime": "09:00:00",
            "endDateTime": "12:00:00",
            "user": {
                "userId": 4,
                "firstName": "ahsan",
                "lastName": "sharif",
                "role": "CANDIDATE"
            }
        },
        {
            "id": 55,
            "date": "2022-10-18T00:00:00.000+00:00",
            "startDateTime": "13:00:00",
            "endDateTime": "15:00:00",
            "user": {
                "userId": 4,
                "firstName": "ahsan",
                "lastName": "sharif",
                "role": "CANDIDATE"
            }
        }
    ]
}