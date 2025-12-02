import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import api from '../api';
import { Plus, BookOpen, User } from 'lucide-react';

const assignSchema = z.object({
    courseId: z.string().min(1, 'Course is required'),
    semester: z.string().min(1, 'Semester is required'),
});

const Students = () => {
    const queryClient = useQueryClient();
    const [selectedStudent, setSelectedStudent] = useState(null);
    const [isAssignModalOpen, setIsAssignModalOpen] = useState(false);
    const [viewEnrollmentsStudent, setViewEnrollmentsStudent] = useState(null);

    const { data: students, isLoading } = useQuery({
        queryKey: ['students'],
        queryFn: async () => {
            const response = await api.get('/students');
            return response.data.content;
        },
    });

    const { data: courses } = useQuery({
        queryKey: ['courses'],
        queryFn: async () => {
            const response = await api.get('/courses');
            return response.data;
        },
    });

    const { data: studentEnrollments } = useQuery({
        queryKey: ['studentEnrollments', viewEnrollmentsStudent?.id],
        queryFn: async () => {
            const response = await api.get(`/students/${viewEnrollmentsStudent.id}/courses`);
            return response.data;
        },
        enabled: !!viewEnrollmentsStudent,
    });

    const assignMutation = useMutation({
        mutationFn: async (data) => {
            await api.post(`/students/${selectedStudent.id}/courses/${data.courseId}?semester=${data.semester}`);
        },
        onSuccess: () => {
            setIsAssignModalOpen(false);
            setSelectedStudent(null);
            alert('Course assigned successfully');
        },
        onError: (error) => {
            console.error(error);
            alert('Failed to assign course');
        },
    });

    const { register, handleSubmit, formState: { errors }, reset } = useForm({
        resolver: zodResolver(assignSchema),
    });

    const openAssignModal = (student) => {
        setSelectedStudent(student);
        setIsAssignModalOpen(true);
        reset();
    };

    const onAssignSubmit = (data) => {
        assignMutation.mutate(data);
    };

    if (isLoading) return <div>Loading...</div>;

    return (
        <div className="space-y-6">
            <div className="flex justify-between items-center">
                <h2 className="text-2xl font-bold text-gray-900">Students</h2>
                {/* Add Create Student Button here if needed */}
            </div>

            <div className="bg-white shadow overflow-hidden sm:rounded-md">
                <ul className="divide-y divide-gray-200">
                    {students?.map((student) => (
                        <li key={student.id} className="px-6 py-4 flex items-center justify-between hover:bg-gray-50">
                            <div>
                                <div className="text-sm font-medium text-indigo-600">{student.name}</div>
                                <div className="text-sm text-gray-500">{student.email}</div>
                            </div>
                            <div className="flex space-x-4">
                                <button
                                    onClick={() => setViewEnrollmentsStudent(student)}
                                    className="text-gray-400 hover:text-gray-500"
                                    title="View Courses"
                                >
                                    <BookOpen className="h-5 w-5" />
                                </button>
                                <button
                                    onClick={() => openAssignModal(student)}
                                    className="text-indigo-600 hover:text-indigo-900"
                                    title="Assign Course"
                                >
                                    <Plus className="h-5 w-5" />
                                </button>
                            </div>
                        </li>
                    ))}
                </ul>
            </div>

            {/* Assign Course Modal */}
            {isAssignModalOpen && (
                <div className="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center p-4">
                    <div className="bg-white rounded-lg max-w-md w-full p-6">
                        <h3 className="text-lg font-medium mb-4">Assign Course to {selectedStudent?.name}</h3>
                        <form onSubmit={handleSubmit(onAssignSubmit)} className="space-y-4">
                            <div>
                                <label className="block text-sm font-medium text-gray-700">Course</label>
                                <select
                                    {...register('courseId')}
                                    className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                                >
                                    <option value="">Select a course</option>
                                    {courses?.map((course) => (
                                        <option key={course.id} value={course.id}>{course.courseName}</option>
                                    ))}
                                </select>
                                {errors.courseId && <p className="text-red-500 text-xs mt-1">{errors.courseId.message}</p>}
                            </div>
                            <div>
                                <label className="block text-sm font-medium text-gray-700">Semester</label>
                                <input
                                    type="text"
                                    {...register('semester')}
                                    className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                                />
                                {errors.semester && <p className="text-red-500 text-xs mt-1">{errors.semester.message}</p>}
                            </div>
                            <div className="flex justify-end space-x-3">
                                <button
                                    type="button"
                                    onClick={() => setIsAssignModalOpen(false)}
                                    className="bg-white py-2 px-4 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 hover:bg-gray-50"
                                >
                                    Cancel
                                </button>
                                <button
                                    type="submit"
                                    className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700"
                                >
                                    Assign
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            {/* View Enrollments Modal */}
            {viewEnrollmentsStudent && (
                <div className="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center p-4">
                    <div className="bg-white rounded-lg max-w-lg w-full p-6">
                        <div className="flex justify-between items-center mb-4">
                            <h3 className="text-lg font-medium">Courses for {viewEnrollmentsStudent.name}</h3>
                            <button onClick={() => setViewEnrollmentsStudent(null)} className="text-gray-400 hover:text-gray-500">
                                <span className="sr-only">Close</span>
                                <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
                                </svg>
                            </button>
                        </div>
                        <div className="mt-2">
                            {studentEnrollments?.length > 0 ? (
                                <ul className="divide-y divide-gray-200">
                                    {studentEnrollments.map((enrollment) => (
                                        <li key={enrollment.id} className="py-3 flex justify-between">
                                            <div>
                                                <p className="text-sm font-medium text-gray-900">{enrollment.courseName}</p>
                                                <p className="text-sm text-gray-500">{enrollment.semester}</p>
                                            </div>
                                            <div className="text-sm text-gray-500">
                                                Grade: {enrollment.grade || 'N/A'}
                                            </div>
                                        </li>
                                    ))}
                                </ul>
                            ) : (
                                <p className="text-sm text-gray-500">No courses enrolled.</p>
                            )}
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Students;
