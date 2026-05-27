import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/company/': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        headers: { origin: 'http://localhost:8080' }
      },
      '/job_listing/': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        headers: { origin: 'http://localhost:8080' }
      },
      '/user/': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        headers: { origin: 'http://localhost:8080' }
      },
      '/tech/': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        headers: { origin: 'http://localhost:8080' }
      },
      '/auth/': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        headers: { origin: 'http://localhost:8080' }
      }
    }
  }
});
