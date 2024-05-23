// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Home from './components/Home';
import UserManagement from './components/UserManagement';
import IdentityVerification from './components/IdentityVerification';

const App = () => {
    return (
        <Router>
            <div>
                <nav>
                    <Link to="/">Home</Link> | <Link to="/hyeonJunTest">User Management</Link>
                </nav>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/hyeonJunTest" element={<UserManagement />} />
                    <Route path="*" element={<h1>또 이러신다..이러지 마시고 /로 가세요</h1>} />
                    <Route path="/hyeonJunTest/IdentityVerification" element={<IdentityVerification />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;
