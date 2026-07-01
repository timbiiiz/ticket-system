import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { apiClient } from "../api/axios";

export default function SignupPage() {
  const navigate = useNavigate();

  // 入力フォームの状態管理
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  // エラー, 成功メッセージの状態管理
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.SyntheticEvent) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      // バックエンドの/api/auth/signupを叩く
      await apiClient.post("/api/auth/signup", {
        username,
        email,
        password,
      });

      // 登録成功したらログイン画面へ
      navigate("/login");
    } catch (err) {
      if (err instanceof Error) {
        setError(err.message);
        console.error(err.message);
      } else {
        setError("アカウント作成に失敗しました。入力内容を確認してください。");
        console.error("予期せぬエラーが発生しました", err);
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      className="relative flex min-h-screen items-center justify-center bg-cover bg-center bg-no-repeat px-4 py-12 sm:px-6 lg:px-8"
      style={{
        // 💡 ログイン画面と共通の最大ぼかし画像を設定
        backgroundImage: `url('https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=1200&q=80&blur=40')`,
      }}
    >
      {/* 💡 背景画像を程よく暗くして文字を浮かび上がらせるオーバーレイ */}
      <div className="absolute inset-0 bg-black/40" />

      {/* 新規会員登録フォームを囲む重厚感のあるすりガラス風コンテナ */}
      <div className="relative z-10 w-full max-w-md space-y-8 rounded-xl bg-black/50 p-8 shadow-2xl backdrop-blur-xl border border-white/10">
        <div>
          <h2 className="mt-6 text-center text-3xl font-extrabold tracking-tight text-white drop-shadow-md">
            新規会員登録
          </h2>
        </div>

        {/* エラーメッセージ表示エリア */}
        {error && (
          <div className="rounded bg-red-500/20 p-3 text-sm text-red-200 border border-red-500/40 backdrop-blur-sm">
            {error}
          </div>
        )}

        <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
          <div className="space-y-5">
            <div>
              <label className="block text-sm font-semibold text-gray-100">
                ユーザー名
              </label>
              <input
                type="text"
                required
                className="mt-1 relative block w-full rounded-md border border-white/20 bg-gray-900/50 px-3 py-2.5 text-white placeholder-gray-400 focus:border-indigo-400 focus:bg-gray-900/80 focus:outline-none sm:text-sm transition-all"
                placeholder="ユーザー名を入力"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </div>
            <div>
              <label className="block text-sm font-semibold text-gray-100">
                メールアドレス
              </label>
              <input
                type="email"
                required
                className="mt-1 relative block w-full rounded-md border border-white/20 bg-gray-900/50 px-3 py-2.5 text-white placeholder-gray-400 focus:border-indigo-400 focus:bg-gray-900/80 focus:outline-none sm:text-sm transition-all"
                placeholder="example@email.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
            <div>
              <label className="block text-sm font-semibold text-gray-100">
                パスワード
              </label>
              <input
                type="password"
                required
                className="mt-1 relative block w-full rounded-md border border-white/20 bg-gray-900/50 px-3 py-2.5 text-white placeholder-gray-400 focus:border-indigo-400 focus:bg-gray-900/80 focus:outline-none sm:text-sm transition-all"
                placeholder="パスワードを入力"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
          </div>

          <div>
            <button
              type="submit"
              disabled={loading}
              className="group relative flex w-full justify-center rounded-md bg-indigo-600 px-3 py-2.5 text-sm font-bold text-white hover:bg-indigo-500 focus-visible:outline focus-visible:outline-offset-2 focus-visible:outline-indigo-600 disabled:bg-indigo-400 transition-colors shadow-lg"
            >
              {loading ? "登録中..." : "アカウントを作成する"}
            </button>
          </div>
        </form>

        <div className="text-center text-sm pt-2">
          <Link
            to="/login"
            className="font-semibold text-indigo-300 hover:text-indigo-200 transition-colors drop-shadow-sm border-b border-indigo-300/30 hover:border-indigo-200"
          >
            すでにアカウントをお持ちの方はこちら（ログイン）
          </Link>
        </div>
      </div>
    </div>
  );
}
