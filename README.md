# Webserver-Test

Testing deno and bun webserver capabilities to be used with a java client. 

Simple representation of bun/deno webserver differences when used with artifactory (jfrog). 

I tried to reproduce an error in Artifactory, where - if the server is bun - artifactory is unable to read the headers correctly. 

Unable to reproduce the error. 

## How to run

Use 3 terminals to run the following commands individually:

```bash
deno run --allow-net --watch main.ts
bun run index.ts

java AccessTest
```


## Results

Bun sends known headers with initial capital letter, unknown headers will keep the original case.

Example:
```
"content-type": "application/json" --> "Content-Type": "application/json"
"x-Custom-header": "custom-value" --> "x-Custom-header": "custom-value"
```

Deno sends all headers in lowercase.

Example:
```
"Content-Type": "application/json" --> "content-type": "application/json"
"x-Custom-header": "custom-value" --> "x-custom-header": "custom-value"
```

Java using the default java.net.* libraries will read the headers as they are sent by the server.

Using `console.log(response)` in both bun and deno will always print any header in lowercase: 

```
GET: /test
Response (11 bytes) {
  ok: true,
  url: "",
  status: 200,
  statusText: "",
  headers: Headers {
    "content-type": "text/plain",
    "access-control-allow-origin": "*",
    "content-length": "11",
    "connection": "keep-alive",
    "last-modified": "Fri, 26 Apr 2024 16:30:54 GMT",
    "x-custom-header": "custom-value",
  },
  redirected: false,
  bodyUsed: false,
  Blob (11 bytes)
}
```

## Artifactory Error

When using bun, artifactory is unable to read the headers correctly: 


Using Deno: 
```
Return Headers of request: 
[
  Accept-Ranges:          [bytes], 
  X-Artifactory-Node-Id:  [artifactory.domain],
  null:                   [HTTP/1.1 200],
  X-Artifactory-Id:       [...:-...:...:-8000],
  X-JFrog-Version:        [Artifactory/7.77.6 77706900],
  Content-Disposition:    [attachment; filename="file.txt"; filename*=UTF-8''file.txt],
  Connection:             [keep-alive],
  Last-Modified:          [Fri, 19 Apr 2024 01:01:19 GMT],
  X-Artifactory-Filename: [file.txt],
  Content-Length:         [10],
  Date:                   [Fri, 19 Apr 2024 13:51:47 GMT],
  Content-Type:           [application/octet-stream]
]
```

Using Bun: 
```
Return Headers of request: 
[
  null:                   [HTTP/1.1 200 OK], 
  Content-Length:         [0], 
  Date:                   [Fri, 19 Apr 2024 14:06:58 GMT]
]
```

## Conclusion

The error is not reproducible with this simple test.
There must be some other factor that is causing the error in Artifactory. 

Since there should be nothing in between the server (bun/deno) and the client ( artifactory-groovy-plugin ), I have no idea what could be causing the error. 

Unless groovy uses different libraries to read the response, this is a mystery. 

