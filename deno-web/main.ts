const port = 3002;

const handler = (request: Request): Response => {
  const url = new URL(request.url)
  console.log(`${request.method}: ${url.pathname}`)

  if (url.pathname === "/test") {
    const body = "Hello Deno!\n";
    const headers = new Headers();
    headers.set("Content-Type", "text/plain");
    headers.set("Access-Control-Allow-Origin", "*");
    headers.set("Content-Length", body.length.toString());
    headers.set("Connection", "keep-alive");
    headers.set("Last-Modified", new Date().toUTCString());
    headers.set("X-custom-header", "custom-value")

    const response = new Response(body, {
      status: 200,
      headers,
    });

    console.log(response);

    return response;
  }

  return new Response("404!\n", { status: 404 });
};

Deno.serve({ port }, handler);