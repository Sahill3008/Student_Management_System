import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import api from '../api';
import { Users } from 'lucide-react';

const Courses = () => {
    const [viewStudentsCourse, setViewStudentsCourse] = useState(null);

    const { data: courses, isLoading } = useQuery({
        queryKey: ['courses'],
        queryFn: async () => {
            const response = await api.get('/courses');
            return response.data;
        },
    });

    const { data: courseEnrollments } = useQuery({
        queryKey: ['courseEnrollments', viewStudentsCourse?.id],
        queryFn: async () => {
            const response = await api.get(`/courses/${viewStudentsCourse.id}/students`);
            return response.data;
        },
        enabled: !!viewStudentsCourse,
    });

    if (isLoading) return <div>Loading...</div>;

    return (
        <div className="space-y-6">
            <div className="flex justify-between items-center">
                <h2 className="text-2xl font-bold text-gray-900">Courses</h2>
            </div>

            <div className="bg-white shadow overflow-hidden sm:rounded-md">
                <ul className="divide-y divide-gray-200">
                    {courses?.map((course) => (
                        <li key={course.id} className="px-6 py-4 flex items-center justify-between hover:bg-gray-50">
                            <div>
                                <div className="text-sm font-medium text-indigo-600">{course.courseName}</div>
                                <div className="text-sm text-gray-500">{course.description}</div>
                                <div className="text-xs text-gray-400">Instructor: {course.instructorName || 'None'}</div>
                            </div>
                            <div className="flex space-x-4">
                                <button
                                    onClick={() => setViewStudentsCourse(course)}
                                    className="text-gray-400 hover:text-gray-500"
                                    title="View Students"
                                >
                                    <Users className="h-5 w-5" />
                                </button>
                            </div>
                        </li>
                    ))}
                </ul>
            </div>

            {/* View Enrolled Students Modal */}
            {viewStudentsCourse && (
                <div className="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center p-4">
                    <div className="bg-white rounded-lg max-w-lg w-full p-6">
                        <div className="flex justify-between items-center mb-4">
                            <h3 className="text-lg font-medium">Students in {viewStudentsCourse.courseName}</h3>
                            <button onClick={() => setViewStudentsCourse(null)} className="text-gray-400 hover:text-gray-500">
                                <span className="sr-only">Close</span>
                                <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
                                </svg>
                            </button>
                        </div>
                        <div className="mt-2">
                            {courseEnrollments?.length > 0 ? (
                                <ul className="divide-y divide-gray-200">
                                    {courseEnrollments.map((enrollment) => (
                                        <li key={enrollment.id} className="py-3 flex justify-between">
                                            <div>
                                                <p className="text-sm font-medium text-gray-900">{enrollment.studentName}</p>
                                                <p className="text-sm text-gray-500">{enrollment.semester}</p>
                                            </div>
                                            <div className="text-sm text-gray-500">
                                                Grade: {enrollment.grade || 'N/A'}
                                            </div>
                                        </li>
                                    ))}
                                </ul>
                            ) : (
                                <p className="text-sm text-gray-500">No students enrolled.</p>
                            )}
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Courses;
