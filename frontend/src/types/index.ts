// ユーザー情報の型定義
export interface User {
  id: number;
  username: string;
  email: string;
  roles: string;
  enabled: boolean;
  createdAt: string;
  updatedAt: string;
}

// チケット情報の型定義
export interface Ticket {
  id: number;
  name: string;
  price: number;
  stock: number;
}

// 購入履歴の型定義
export interface Booking {
  id: number;
  username: string;
  ticketId: number;
  purchaseAt: string;
}

// ログイン成功時のレスポンス型定義
export interface JwtResponse {
  token: string;
  type: string; // "Bearer" が入る
}
