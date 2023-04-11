# INTRODUCTION
This application receive updates from a partner service, transform these updates and provide the aggregated data through an endpoint.

Steps to run:
1) Start partner service: `java -jar partner-service-1.0-all.jar --port=8032`
2) Start application

Select a isin from incoming events on console and execute the following 

REQUEST:

`GET : /candlestick?isin=DX4104130621 //id
`
This request takes isin value as request param.

RESPONSE:

`{
"isin": "DX4104130621",
"openTimestamp": "2022-01-07T15:42:41.234170Z",
"closeTimestamp": "2022-01-07T15:42:39.533046Z",
"highPrice": 1429.1975,
"lowPrice": 1386.4459,
"openPrice": 1425.6943,
"closedPrice": 1429.1975
}`
