import http from './http';
import { Student } from '@/types/Student';

const base = '/api/students';

export const getStudents = () => http.get(`${base}`);
export const getStudent = (id: number) => http.get(`${base}/${id}`);
export const addStudent = (student: Student) => http.post(`${base}`, student);
export const updateStudent = (id: number, student: Student) => http.put(`${base}/${id}`, student);
export const deleteStudent = (id: number) => http.delete(`${base}/${id}`);