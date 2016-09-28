# nhs-conditions-scrapper
micro service that scrapes the nhsChoice website and caches the condition pages and their subpages content into a database. It allows basic querying of pages content 


## (Tiny) Description
There are two main classes under the package caguilera.assessment.nhs.main:
- **NhsConditionsScraper**: Scraps the NHS Conditions website and stores all the condition subpages into a database (To be run preferably _only_ once)
- **NhsConditionsRestService**: Rest service that runs on localhost (port 8080) and points the user to the most appropriate pages for his requests. Examples of queries are: http://localhost:8080/ask?text=cancer, http://localhost:8080/ask?text=what%20are%20the%20symptoms%20of%20cancer or http://localhost:8080/ask?text=treatments%20for%20headaches
