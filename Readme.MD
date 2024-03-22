Client 1 & 2 connect
```
ws://192.168.50.233:8085?room=room_id&token=eyJhbGciOiJIUzUxMiJ9....
```

Client 1:
- event send:
```
send_message
```
- message:
```
{
    "message": "from client 1",
    "type": "CLIENT"
}
```
- listener event:
```
get_message
```
- received message:
```
{"messageType":"TEXT","sender":"SYSTEM","message":"from client 2"}
```


Client 2:
- event send:
```
send_message
```
- message:
```
{
    "message": "from client 2",
    "type": "CLIENT"
}
```
- listener event:
```
get_message
```
- received message:
```
{"messageType":"TEXT","sender":"SYSTEM","message":"from client 1"}
```