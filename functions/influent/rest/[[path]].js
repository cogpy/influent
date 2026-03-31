/**
 * Cloudflare Pages Function – proxy /influent/rest/* to the Java backend.
 *
 * Set the BACKEND_URL environment variable in the Cloudflare Pages dashboard
 * to the base URL of your Java backend (e.g. https://api.example.com).
 * Requests are forwarded as-is, preserving method, headers, and body.
 */
export async function onRequest(context) {
  const { request, env } = context;

  const backendUrl = env.BACKEND_URL;
  if (!backendUrl) {
    return new Response(
      JSON.stringify({ error: 'BACKEND_URL environment variable is not configured' }),
      { status: 500, headers: { 'Content-Type': 'application/json' } }
    );
  }

  const url = new URL(request.url);
  const target = `${backendUrl.replace(/\/$/, '')}${url.pathname}${url.search}`;

  const proxyHeaders = new Headers(request.headers);
  proxyHeaders.set('X-Forwarded-Host', url.host);
  proxyHeaders.set('X-Forwarded-Proto', url.protocol.replace(':', ''));

  // Clone the request before reading its body to avoid consuming the stream.
  const cloned = request.clone();
  return fetch(target, {
    method: cloned.method,
    headers: proxyHeaders,
    body: ['GET', 'HEAD'].includes(cloned.method) ? undefined : cloned.body,
    redirect: 'follow',
  });
}
