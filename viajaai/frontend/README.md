This folder is intended to hold the frontend for the `viajaai` instance.

Current repository has a top-level `frontend/` directory that contains the React app used by ViajaAI.

To complete the per-instance separation, copy the contents of the top-level `frontend/` into this folder (preserve `package.json`, `public/` and `src/`).

After copying, you can run the frontend for this instance with:

```
cd viajaai/frontend
npm install
npm start
```
