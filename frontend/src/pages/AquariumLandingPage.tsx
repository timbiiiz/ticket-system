import { Link } from "react-router-dom";

export default function AquariumLandingPage() {
  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 font-sans overflow-x-hidden">
      {/* ヒーローセクション */}
      <header
        className="relative h-screen flex items-center justify-center bg-cover bg-center bg-no-repeat"
        style={{
          backgroundImage: `url('https://images.unsplash.com/photo-1544551763-46a013bb70d5?auto=format&fit=crop&w=1920&q=80&blur=30')`,
        }}
      >
        {/* ディープブルーのオーバーレイ */}
        <div className="absolute inset-0 bg-linear-to-b from-slate-950/30 via-slate-950/50 to-slate-950" />

        {/* メイン */}
        <div className="relative z-10 text-center px-4 max-w-4xl animate-fade-in">
          <span className="text-cyan-400 text-sm font-bold tracking-widest uppercase block mb-3 animate-pulse">
            ー 光と水が織りなす、神秘のセカイ ー
          </span>
          <h1 className="text-4xl sm:text-6xl font-black tracking-tight text-white leading-tight drop-shadow-lg">
            青の美しさに、
            <br className="sm:hidden" />
            息をのむ。
          </h1>
          <p className="mt-6 text-base sm:text-lg text-slate-200 max-w-xl mx-auto leading-relaxed drop-shadow">
            巨大なクラゲの揺らぎ、頭上を回遊するサメたちの迫力。日常を忘れ、深い海の底へと旅に出かけませんか。
          </p>
          <div className="mt-10 flex flex-col sm:flex-row gap-4 justify-center items-center">
            <Link
              to="/login"
              className="w-full sm:w-auto px-8 py-4 rounded-full bg-linear-to-r from-cyan-500 to-blue-600 text-white font-bold text-center shadow-lg shadow-cyan-500/20 hover:from-cyan-400 hover:to-blue-500 transform hover:-translate-y-0.5 transition-all"
            >
              オンラインチケットを購入
            </Link>
            <a
              href="#attractions"
              className="w-full sm:w-auto px-8 py-4 rounded-full bg-slate-900/60 backdrop-blur-md border border-slate-700 text-slate-300 font-bold text-center hover:bg-slate-800 hover:text-white transition-all"
            >
              見どころを見る
            </a>
          </div>
        </div>

        {/* 下部へのスクロール誘導 */}
        <div className="absolute bottom-8 left-1/2 transform -translate-x-1/2 text-slate-400 text-xs tracking-widest flex flex-col items-center gap-2">
          <span>SCROLL</span>
          <div className="w-px h-12 bg-linear-to-b from-cyan-500 to-transparent animate-bounce" />
        </div>
      </header>

      {/* アトラクション・見どころセクション */}
      <section
        id="attractions"
        className="py-24 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto"
      >
        <div className="text-center mb-16">
          <p className="text-cyan-400 text-xs font-bold tracking-widest uppercase mb-2">
            Highlights
          </p>
          <h2 className="text-3xl font-black text-white sm:text-4xl">
            3つの神秘的なエリア
          </h2>
          <div className="h-1 w-12 bg-cyan-500 mx-auto mt-4 rounded-full" />
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {/* エリア1 */}
          <div className="group rounded-2xl overflow-hidden bg-slate-900/40 border border-slate-800/80 hover:border-cyan-500/30 transition-all duration-300 shadow-xl">
            <div className="h-56 overflow-hidden relative">
              <img
                src="https://images.unsplash.com/photo-1546026423-cc4642628d2b?auto=format&fit=crop&w=600&q=80"
                alt="深海大水槽"
                className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
              />
              <div className="absolute top-4 left-4 bg-slate-950/70 backdrop-blur-md px-3 py-1 rounded-full text-xs font-bold text-cyan-400 border border-cyan-500/20">
                ZONE 01
              </div>
            </div>
            <div className="p-6">
              <h3 className="text-xl font-bold text-white group-hover:text-cyan-300 transition-colors">
                極みの青
              </h3>
              <p className="mt-3 text-sm text-slate-400 leading-relaxed">
                日本最大級のシースルーシアター。光の届かない漆黒の深海でたくましく生きる、巨大な群泳を等身大で体感。
              </p>
            </div>
          </div>

          {/* エリア2 */}
          <div className="group rounded-2xl overflow-hidden bg-slate-900/40 border border-slate-800/80 hover:border-cyan-500/30 transition-all duration-300 shadow-xl">
            <div className="h-56 overflow-hidden relative">
              <img
                src="https://images.unsplash.com/photo-1478479405421-ce83c92fb3ba?auto=format&fit=crop&w=600&q=80"
                alt="クラゲ万華鏡"
                className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
              />
              <div className="absolute top-4 left-4 bg-slate-950/70 backdrop-blur-md px-3 py-1 rounded-full text-xs font-bold text-cyan-400 border border-cyan-500/20">
                ZONE 02
              </div>
            </div>
            <div className="p-6">
              <h3 className="text-xl font-bold text-white group-hover:text-cyan-300 transition-colors">
                クラゲリウム
              </h3>
              <p className="mt-3 text-sm text-slate-400 leading-relaxed">
                壁面すべてが鏡で覆われた、幻想的なシンクロ空間。光の演出とともに、ゆらゆらと浮遊するクラゲに癒やされます。
              </p>
            </div>
          </div>

          {/* エリア3 */}
          <div className="group rounded-2xl overflow-hidden bg-slate-900/40 border border-slate-800/80 hover:border-cyan-500/30 transition-all duration-300 shadow-xl">
            <div className="h-56 overflow-hidden relative">
              <img
                src="https://images.unsplash.com/photo-1518156677180-95a2893f3e9f?auto=format&fit=crop&w=600&q=80"
                alt="水中トンネル"
                className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
              />
              <div className="absolute top-4 left-4 bg-slate-950/70 backdrop-blur-md px-3 py-1 rounded-full text-xs font-bold text-cyan-400 border border-cyan-500/20">
                ZONE 03
              </div>
            </div>
            <div className="p-6">
              <h3 className="text-xl font-bold text-white group-hover:text-cyan-300 transition-colors">
                海底トンネルアクアロード
              </h3>
              <p className="mt-3 text-sm text-slate-400 leading-relaxed">
                360度すべてが透明なアクリルに囲まれた、まるで海底を散歩しているかのようなダイナミックな感動体験。
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* 営業案内 インフォメーション */}
      <section className="bg-slate-900/40 border-y border-slate-900 py-20 px-4 sm:px-6 lg:px-8">
        <div className="max-w-5xl mx-auto grid grid-cols-1 md:grid-cols-2 gap-12 items-center">
          <div>
            <p className="text-cyan-400 text-xs font-bold tracking-widest uppercase mb-2">
              Information
            </p>
            <h2 className="text-3xl font-black text-white mb-6">
              営業日のご案内
            </h2>
            <div className="space-y-4 text-slate-300 text-sm">
              <div className="flex justify-between border-b border-slate-800 pb-2">
                <span className="font-semibold">開館時間</span>
                <span>9:00 〜 21:00 （最終入館 20:00）</span>
              </div>
              <div className="flex justify-between border-b border-slate-800 pb-2">
                <span className="font-semibold">休館日</span>
                <span>年中無休（設備点検日を除く）</span>
              </div>
              <div className="flex justify-between border-b border-slate-800 pb-2">
                <span className="font-semibold">アクセス</span>
                <span>アクアシティ駅下車、中央改札より徒歩3分</span>
              </div>
            </div>
            <p className="mt-6 text-xs text-slate-400 bg-cyan-950/20 border border-cyan-900/30 rounded-lg p-3">
              ⚠️
              混雑緩和のため、土日祝日のご入館には事前オンラインチケットのご購入をおすすめしております。
            </p>
          </div>
          <div className="rounded-2xl overflow-hidden shadow-2xl border border-slate-800">
            {/* 地図の代わりとなるイメージ画 */}
            <img
              src="https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?auto=format&fit=crop&w=800&q=80"
              alt="外観"
              className="w-full h-64 object-cover"
            />
          </div>
        </div>
      </section>

      {/* 購入へ誘導 */}
      <section className="py-24 text-center px-4 relative">
        <div className="absolute inset-0 bg-linear-to-t from-cyan-950/20 to-transparent pointer-events-none" />
        <div className="relative z-10 max-w-2xl mx-auto">
          <h2 className="text-3xl sm:text-4xl font-black text-white tracking-tight">
            スマート入場、はじめよう。
          </h2>
          <p className="mt-4 text-slate-300 text-sm sm:text-base leading-relaxed">
            オンラインチケットなら、スマホひとつで当日並ばずにスムーズに入場できます。
          </p>
          <div className="mt-8">
            <Link
              to="/login"
              className="inline-block px-10 py-4 rounded-full bg-linear-to-r from-cyan-400 to-blue-500 text-white font-extrabold text-lg shadow-xl shadow-cyan-500/30 hover:from-cyan-300 hover:to-blue-400 transform hover:scale-[1.02] transition-all"
            >
              今すぐチケットを見に行く
            </Link>
          </div>
        </div>
      </section>

      {/* フッター */}
      <footer className="border-t border-slate-900 py-8 text-center text-xs text-slate-500">
        <p>© 2026 AQUA PASSPORT Inc. All rights reserved.</p>
      </footer>
    </div>
  );
}
