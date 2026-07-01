import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiClient } from "../api/axios";
import type { Ticket } from "../types";

export default function TicketListPage() {
  const navigate = useNavigate();
  const [tickets, setTickets] = useState<Ticket[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");
  const [countdown, setCountdown] = useState(5);

  // ページ表示時にチケット一覧を取得
  const fetchTickets = useCallback(async () => {
    try {
      const response = await apiClient.get<Ticket[]>("/api/tickets");
      setTickets(response.data);
    } catch (err) {
      if (err instanceof Error) {
        setError(err.message);
        console.error(err.message);
      } else {
        setError("チケットの取得に失敗しました");
        console.error("チケットの取得に失敗しました", err);
      }
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    if (!localStorage.getItem("token")) {
      navigate("/login");
      return;
    }
    (async () => {
      await fetchTickets();
    })();
  }, [navigate, fetchTickets]);

  // 💡 購入成功時の自動遷移タイマーを制御する useEffect
  useEffect(() => {
    // メッセージが空、または購入成功（🐋が含まれる）以外のときは何もしない
    if (!message || !message.includes("🐋")) return;

    // 1. 1秒ごとにカウントダウンの数値を減らすタイマー
    const countdownTimer = setInterval(() => {
      setCountdown((prev) => (prev > 0 ? prev - 1 : 0));
    }, 1000);

    // 2. 5秒後にホームへ自動遷移するタイマー
    const redirectTimer = setTimeout(() => {
      navigate("/home");
    }, 5000);

    // コンポーネント消滅時や再実行時にタイマーをクリアしてバグを防ぐ
    return () => {
      clearInterval(countdownTimer);
      clearTimeout(redirectTimer);
    };
  }, [message, navigate]);

  // チケット購入処理
  const handlePurchase = async (ticketId: number, ticketName: string) => {
    setError("");
    setMessage("");
    setCountdown(5); // 購入ボタンを押した瞬間にカウントダウンを5秒にリセット

    try {
      await apiClient.post("/api/bookings", { ticketId });
      setMessage(
        `🐋 ${ticketName} の購入が完了しました！楽しいひとときをお過ごしください！`,
      );
      fetchTickets();
    } catch (err) {
      if (err instanceof Error) {
        setError("購入処理に失敗しました。在庫状況を確認してください");
        console.error(err.message);
      } else {
        setError("購入処理に失敗しました");
        console.error("購入処理に失敗しました", err);
      }
    }
  };

  // ログアウト処理
  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  if (loading) {
    return (
      <div className="flex min-h-screen items-center justify-center bg-slate-900 text-cyan-400 font-medium text-xl">
        <div className="animate-pulse">🌊 水のセカイを読み込み中...</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-linear-to-b from-slate-900 via-sky-950 to-slate-900 text-slate-100 pb-12">
      <nav className="bg-slate-900/80 backdrop-blur-md border-b border-cyan-500/20 sticky top-0 z-50">
        <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
          <div className="flex h-16 justify-between items-center">
            <div className="flex items-center space-x-2">
              <span className="text-2xl">🐋</span>
              <h1 className="text-xl font-extrabold tracking-wider bg-linear-to-r from-cyan-400 to-blue-400 bg-clip-text text-transparent">
                AQUA PASSPORT{" "}
                <span className="text-xs font-medium text-cyan-300/70 block sm:inline sm:ml-2">
                  水族館オンライン発券
                </span>
              </h1>
            </div>
            <button
              onClick={handleLogout}
              className="rounded-full bg-slate-800 border border-slate-700 hover:border-cyan-500/50 px-5 py-1.5 text-sm font-semibold text-slate-300 hover:text-cyan-400 transition-all duration-300 shadow-sm"
            >
              ログアウト
            </button>
          </div>
        </div>
      </nav>

      {/* メインコンテンツ */}
      <div className="mx-auto max-w-7xl px-4 py-10 sm:px-6 lg:px-8">
        {/* メッセージ通知エリア */}
        {message && (
          <div className="mb-8 rounded-xl bg-cyan-500/10 p-4 text-sm text-cyan-200 border border-cyan-500/30 font-semibold shadow-lg backdrop-blur-sm animate-fade-in flex flex-col sm:flex-row sm:justify-between sm:items-center gap-2">
            <div>{message}</div>
            {/* メッセージの右側にカウントダウンメーターを表示 */}
            <div className="text-xs bg-cyan-500/20 text-cyan-300 px-3 py-1 rounded-full border border-cyan-500/30 whitespace-nowrap self-start sm:self-auto">
              {" "}
              <span className="font-bold text-white text-sm">
                {countdown}
              </span>{" "}
              秒後にホームへ戻ります
            </div>
          </div>
        )}
        {error && (
          <div className="mb-8 rounded-xl bg-red-500/10 p-4 text-sm text-red-300 border border-red-500/30 font-semibold shadow-lg backdrop-blur-sm">
            {error}
          </div>
        )}

        {/* 導入テキスト */}
        <div className="text-center mb-12">
          <p className="text-cyan-400 text-xs font-bold tracking-widest uppercase mb-2">
            Ticket Selection
          </p>
          <h2 className="text-2xl sm:text-3xl font-black text-white">
            入館チケット一覧
          </h2>
          <div className="h-1 w-12 bg-cyan-500 mx-auto mt-4 rounded-full" />
        </div>

        {/* チケット一覧カードグリッド */}
        <div className="grid grid-cols-1 gap-8 sm:grid-cols-2 lg:grid-cols-3">
          {tickets.map((ticket) => (
            <div
              key={ticket.id}
              className="group relative overflow-hidden rounded-2xl bg-slate-900/60 border border-slate-800 hover:border-cyan-500/40 p-6 flex flex-col justify-between shadow-xl transition-all duration-300 hover:-translate-y-1 backdrop-blur-md"
            >
              <div className="absolute top-0 left-0 right-0 h-0.5 bg-linear-to-r from-transparent via-cyan-500/50 to-transparent opacity-0 group-hover:opacity-100 transition-opacity" />

              <div>
                <div className="flex items-start justify-between">
                  <h3 className="text-xl font-bold text-white group-hover:text-cyan-300 transition-colors">
                    {ticket.name}
                  </h3>
                  <span className="text-xl">
                    {ticket.name.includes("子供") ? "🐧" : "🐬"}
                  </span>
                </div>

                <p className="text-xs text-slate-400 mt-1">
                  当日窓口に並ばず、そのままご入館いただけます。
                </p>

                <p className="mt-6 text-3xl font-black text-cyan-400 tracking-tight">
                  ¥{ticket.price.toLocaleString()}
                  <span className="text-xs font-normal text-slate-400 ml-1">
                    / 税込
                  </span>
                </p>

                <div className="mt-6 flex items-center justify-between text-xs border-t border-slate-800/80 pt-4">
                  <span className="text-slate-400 font-medium">
                    現在の販売状況
                  </span>
                  <span
                    className={`font-bold px-2.5 py-0.5 rounded-full text-[11px] ${
                      ticket.stock > 5
                        ? "bg-emerald-500/10 text-emerald-400 border border-emerald-500/20"
                        : ticket.stock > 0
                          ? "bg-amber-500/10 text-amber-400 border border-amber-500/20"
                          : "bg-red-500/10 text-red-400 border border-red-500/20"
                    }`}
                  >
                    {ticket.stock > 0 ? `残り ${ticket.stock} 枚` : "完売御礼"}
                  </span>
                </div>
              </div>

              <div className="mt-8">
                <button
                  onClick={() => handlePurchase(ticket.id, ticket.name)}
                  disabled={ticket.stock <= 0}
                  className={`w-full rounded-xl py-3 text-center text-sm font-bold shadow-md transition-all duration-300 focus:outline-none
                    ${
                      ticket.stock > 0
                        ? "bg-linear-to-r from-cyan-500 to-blue-600 text-white hover:from-cyan-400 hover:to-blue-500 active:scale-[0.98] shadow-cyan-950"
                        : "bg-slate-800 text-slate-500 border border-slate-700/50 cursor-not-allowed shadow-none"
                    }`}
                >
                  {ticket.stock > 0
                    ? "このチケットを購入する"
                    : "完売いたしました"}
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
