import { UserConfigFn } from 'vite';
import { overrideVaadinConfig } from './vite.generated';

const customConfig: UserConfigFn = (env) => ({
  server: {
    port: 3000,  // Run Vite dev server on port 3000
    proxy: {
      '/api': {
        target: 'http://localhost:8081',  // Proxy API requests to Spring Boot backend on port 8081
        changeOrigin: true,
        secure: false,
      },
    },
  },
  // You can add other custom Vite parameters here
  // https://vitejs.dev/config/
});

export default overrideVaadinConfig(customConfig);
