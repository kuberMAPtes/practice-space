// src/components/UserManagement.js
import axios from 'axios';
import React, { useEffect, useState } from 'react';

const UserManagement = () => {
    const [users, setUsers] = useState([]);
    const [newUser, setNewUser] = useState({ name: '', email: '' });
    const [editUser, setEditUser] = useState(null);

    // 유저 목록을 가져오는 함수
    const fetchUsers = async () => {
        try {
            const response = await axios.get(process.env.REACT_APP_API_BASE_URL+"/hyeonJunTest/api/users/");
            setUsers(response.data);
        } catch (error) {
            console.error('Error fetching users', error);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, []); // 처음 이 컴포넌트를 마운트 할 경우 유저 목록을 가져와 리렌더링 될 때 마다 실행되지는 않아.

    // 새로운 유저를 추가하는 함수
    const addUser = async () => {
        try {
            const response = await axios.post(process.env.REACT_APP_API_BASE_URL+'/hyeonJunTest/api/users/add', newUser);
            console.log("실행중");
            setUsers([...users, response.data]);
            setNewUser({ name: '', email: '' });
        } catch (error) {
            console.error('Error adding user', error);
        }
    };

    // 유저를 업데이트하는 함수
    const updateUser = async () => {
        try {
            const response = await axios.put(process.env.REACT_APP_API_BASE_URL+`/hyeonJunTest/api/users/update/${editUser.id}`, editUser);
            setUsers(users?.map(user => user.id === editUser.id ? response.data : user));
            setEditUser(null);
        } catch (error) {
            console.error('Error updating user', error);
        }
    };

    // 유저를 삭제하는 함수
    const deleteUser = async (id) => {
        try {
            await axios.delete(process.env.REACT_APP_API_BASE_URL+`/hyeonJunTest/api/users/delete/${id}`);
            setUsers(users?.filter(user => user.id !== id));
        } catch (error) {
            console.error('Error deleting user', error);
        }
    };

    return (
        <div>
            <h1>User Management</h1>

            <h2>Add User</h2>
            <input
                type="text"
                placeholder="Name"
                value={newUser.name}
                onChange={(e) => setNewUser({ ...newUser, name: e.target.value })}
            />
            <input
                type="email"
                placeholder="Email"
                value={newUser.email}
                onChange={(e) => setNewUser({ ...newUser, email: e.target.value })}
            />
            <button onClick={addUser}>Add User</button>

            <h2>Users</h2>
            <ul>
                {users?.map(user => (
                    <li key={user.id}>
                        {user.name} {user.email}
                        <button onClick={() => deleteUser(user.id)}>Delete</button>
                        <button onClick={() => setEditUser(user)}>Edit</button>
                    </li>
                ))}
            </ul>

            {editUser && (
                <div>
                    <h2>Edit User</h2>
                    <input
                        type="text"
                        placeholder="Name"
                        value={editUser.name}
                        onChange={(e) => setEditUser({ ...editUser, name: e.target.value })}
                    />
                    <input
                        type="email"
                        placeholder="Email"
                        value={editUser.email}
                        onChange={(e) => setEditUser({ ...editUser, email: e.target.value })}
                    />
                    <button onClick={updateUser}>Update User</button>
                </div>
            )}
        </div>
    );
};

export default UserManagement;
