#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")"

pid="$(lsof -ti :8081 || true)"
if [[ -n "$pid" ]]; then
  echo "Stopping existing process on 8081: $pid"
  kill -9 $pid
fi

echo "Starting backend on http://localhost:8081/api ..."
./mvnw spring-  :run
