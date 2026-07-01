import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import SignupPage from "./pages/SignupPage";
import TicketListPage from "./pages/TicketListPage";
import AquariumLandingPage from "./pages/AquariumLandingPage";

function App() {
  return (
    <BrowserRouter>
      <div className="min-h-screen bg-gray-50 text-gray-900">
        <Routes>
          {/* コンポーネントをマッピング */}
          <Route path="/home" element={<AquariumLandingPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<SignupPage />} />
          <Route path="/ticket" element={<TicketListPage />} />

          {/* 存在しないURLはすべてトップにリダイレクト */}
          <Route path="*" element={<Navigate to="/home" replace />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}
export default App;
