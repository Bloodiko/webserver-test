// Simeple webserver returning "Hello Bun" on /test

Bun.serve({
  port: 3001,
  fetch(req) {
    const url = new URL(req.url)
    console.log(`${req.method}: ${url.pathname}`)
    if (url.pathname === "/test") {
      const body = "Hello Bun!\n";
      const headers = new Headers();
      headers.set("content-type", "text/plain");
      headers.set("Access-Control-Allow-Origin", "*");
      headers.set("content-length", body.length.toString());
      headers.set("connection", "keep-alive");
      headers.set("last-modified", new Date().toUTCString());
      headers.set("X-custom-header", "custom-value")

      const response = new Response(body, {
        status: 200,
        headers,
      });

      console.log(response)

      return response;
    }
    return new Response("404!\n", { status: 404 });
  },
});