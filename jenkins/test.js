const request = require('supertest');
const app = require('./app');

describe('GET / (Root Route)', () => {
    it('should respond with a 200 status code and success message', async () => {
        const response = await request(app).get('/');
        expect(response.statusCode).toBe(200);
        expect(response.body.status).toBe('success');
        expect(response.body.message).toContain('Jenkins 101');
    });
});

describe('GET /health (Health Check Route)', () => {
    it('should respond with 200 status code and UP status', async () => {
        const response = await request(app).get('/health');
        expect(response.statusCode).toBe(200);
        expect(response.body.status).toBe('UP');
        expect(response.body).toHaveProperty('uptime');
        expect(response.body).toHaveProperty('timestamp');
    });
});