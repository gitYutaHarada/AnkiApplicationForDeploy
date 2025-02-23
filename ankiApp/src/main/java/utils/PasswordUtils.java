package utils;

import java.security.MessageDigest;
import java.util.Base64;

public class PasswordUtils {
	public String hashPass(String pass) {
		try {
			//ハッシュ関数のインスタンスの生成
			MessageDigest messagedigest = MessageDigest.getInstance("SHA-256");
			//String型のパスワードをバイト配列に変換
			byte[] passByte = pass.getBytes();
			//バイト配列をハッシュ計算の入力データとして渡す
			messagedigest.update(passByte);
			//messagedigest.digestを呼ぶことでハッシュ地の計算をする
			byte[] digest = messagedigest.digest();
			//Base64.Encoderを使ってBase64エンコード用のオブジェクトを取得。
			//encoderはBase64.Encoder型のオブジェクトとなる
			//Base64 は バイナリデータを文字列で表現するためのエンコーディング方式 で、主にデータを安全に保存・送信する際に使われる。
			Base64.Encoder encoder = Base64.getEncoder();
			//encoder.encodeToString(byte[])で"SHA-256"でハッシュ化したbyte[]をBase64形式のString型に変換
			String encodedDigest = encoder.encodeToString(digest);
			return encodedDigest;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isHashPass(String inputPass, String hashPass) {
		try {
			MessageDigest messagedigest = MessageDigest.getInstance("SHA-256");
			byte[] inputPassByte = inputPass.getBytes();
			messagedigest.update(inputPassByte);
			byte[] digest = messagedigest.digest();
			Base64.Encoder encoder = Base64.getEncoder();
			String encodedDigest = encoder.encodeToString(digest);
			return encodedDigest.equals(hashPass);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
