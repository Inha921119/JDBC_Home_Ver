package com.Home.example.JAM.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.Home.example.JAM.util.DBUtil;
import com.Home.example.JAM.util.SecSql;

public class ArticleDao extends Dao {
	public ArticleDao(Connection conn) {
		this.conn = conn;
	}

	public int doWrite(String title, String body, int loginedMemberId) {

		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append(", writerId = ?", loginedMemberId);
		
		return DBUtil.insert(conn, sql);
	}

	public List<Map<String, Object>> getArticles() {
		
		SecSql sql = new SecSql();

		sql.append("SELECT a.*, m.name AS writerName");
		sql.append("FROM article AS a");
		sql.append("INNER JOIN `member` AS m");
		sql.append("ON a.writerId = m.id");
		sql.append("ORDER BY id DESC");
		
		return DBUtil.selectRows(conn, sql);
	}

	public int getArtcleCount(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		return DBUtil.selectRowIntValue(conn, sql);
	}

	public void doModify(int id, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", id);

		DBUtil.update(conn, sql);
	}

	public Map<String, Object> getArticle(int id) {
		
		SecSql sql = new SecSql();

		sql.append("SELECT a.*, m.name AS writerName");
		sql.append("FROM article AS a");
		sql.append("INNER JOIN `member` AS m");
		sql.append("ON a.writerId = m.id");
		sql.append("WHERE a.id = ?", id);
		sql.append("ORDER BY id DESC");
		
		return DBUtil.selectRow(conn, sql);
	}

	public void doDelete(int id) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);

		DBUtil.delete(conn, sql);
	}
}
