import React, { useEffect, useState } from 'react';
import api from '../services/api';
import { Users, BookOpen, Building2 } from 'lucide-react';

const StatCard = ({ title, value, icon: Icon, color }) => (
    <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
        <div className="flex items-center justify-between">
            <div>
                <p className="text-sm font-medium text-gray-500">{title}</p>
                <p className="text-3xl font-bold mt-2 text-gray-900">{value}</p>
            </div>
            <div className={`p-3 rounded-lg ${color}`}>
                <Icon className="h-6 w-6 text-white" />
            </div>
        </div>
    </div>
);

const Dashboard = () => {
    const [stats, setStats] = useState({
        students: 0,
        courses: 0,
        departments: 0
    });

    useEffect(() => {
        const fetchStats = async () => {
            try {
                const [studentsRes, coursesRes, deptsRes] = await Promise.all([
                    api.get('/students'),
                    api.get('/courses'),
                    api.get('/departments')
                ]);

                // Handle pagination response for students if applicable, or list
                const studentCount = studentsRes.data.content ? studentsRes.data.totalElements : studentsRes.data.length || 0;

                setStats({
                    students: studentCount,
                    courses: coursesRes.data.length || 0,
                    departments: deptsRes.data.length || 0
                });
            } catch (error) {
                console.error('Error fetching stats:', error);
            }
        };

        fetchStats();
    }, []);

    return (
        <div className="space-y-6">
            <h1 className="text-2xl font-bold text-gray-900">Dashboard Overview</h1>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                <StatCard
                    title="Total Students"
                    value={stats.students}
                    icon={Users}
                    color="bg-blue-500"
                />
                <StatCard
                    title="Active Courses"
                    value={stats.courses}
                    icon={BookOpen}
                    color="bg-emerald-500"
                />
                <StatCard
                    title="Departments"
                    value={stats.departments}
                    icon={Building2}
                    color="bg-violet-500"
                />
            </div>
        </div>
    );
};

export default Dashboard;
