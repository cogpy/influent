#!/usr/bin/env bash
# build-cloudflare.sh – Build an Influent app WAR and extract its static
# assets into dist/ for deployment to Cloudflare Pages.
#
# Usage:
#   ./build-cloudflare.sh [APP]
#
#   APP  – Maven module to build. One of: influent-app, bitcoin, kiva, walker
#          Defaults to influent-app.
#
# After this script completes, deploy with:
#   wrangler pages deploy dist/ --project-name influent
#
# Prerequisites: Java 8+, Maven 3.1+, unzip

set -euo pipefail

APP="${1:-influent-app}"

VALID_APPS="influent-app bitcoin kiva walker"
if ! echo "$VALID_APPS" | tr ' ' '\n' | grep -qx "$APP"; then
  echo "ERROR: Unknown app '$APP'. Choose one of: $VALID_APPS" >&2
  exit 1
fi

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

echo "==> Building $APP (this may take a few minutes)..."
mvn clean package \
  --projects "$APP" \
  --also-make \
  --define skipTests \
  --define environment=deployment \
  --batch-mode \
  --quiet

WAR_FILE=$(find "$APP/target" -maxdepth 1 -name "${APP}-*.war" 2>/dev/null | head -n 1)
if [ -z "$WAR_FILE" ]; then
  echo "ERROR: No WAR file found in $APP/target/" >&2
  exit 1
fi

echo "==> Extracting $WAR_FILE → dist/"
rm -rf dist
mkdir -p dist
unzip -q "$WAR_FILE" -d dist

# Remove Java-specific server directories – not needed for static hosting.
rm -rf dist/WEB-INF dist/META-INF

echo "==> dist/ is ready for Cloudflare Pages."
echo ""
echo "    Deploy with:"
echo "      wrangler pages project create influent   # first time only"
echo "      wrangler pages deploy dist/ --project-name influent"
