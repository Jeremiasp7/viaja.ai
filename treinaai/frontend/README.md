TreinaAI frontend (minimal scaffold)

How to run

1. From `treinaai/frontend` run:

```powershell
npm install
npm start
```

2. The dev server proxies `/api` to `http://localhost:8080` by default (see `webpack.config.js`). Ensure your backend is running.

Notes
- This is a minimal React + Webpack scaffold to begin integrating the UI with the TreinaAI backend.
- Endpoints used: `/api/auth/login`, `/api/suggestions/plan`, `/api/recommendations/object`.
This folder is intended to hold the frontend for the `treinaai` instance.

Copy the top-level `frontend/` React app into this folder to give `treinaai` its own frontend.

Run it with:

```
cd treinaai/frontend
npm install
npm start
```
