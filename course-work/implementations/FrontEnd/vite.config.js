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
        headers: { origin: 'http://localhost:8080' },
        bypass: (req) => {
          if (req.headers.accept?.includes('text/html')) {
            return req.url;
          }
          return undefined;
        }
      },
      '/auth/': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        headers: { origin: 'http://localhost:8080' }
      }
      ,
      '/job_application/': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        headers: { origin: 'http://localhost:8080' }
      },
      '/job_listing_tech/': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        headers: { origin: 'http://localhost:8080' }
      }
    }
  }
});
