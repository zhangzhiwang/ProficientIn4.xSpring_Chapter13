package com.asiainfo.entity;

import java.sql.Timestamp;
import java.util.Arrays;

public class Post {
	private int postId;
	private int topicId;
	private int forumId;
	private int userId;
	private String postText;// 对应数据库的CLOB数据
	private byte[] postAttach;// 对应数据库的BLOB数据
	private Timestamp postTime;

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public byte[] getPostAttach() {
		return postAttach;
	}

	public void setPostAttach(byte[] postAttach) {
		this.postAttach = postAttach;
	}

	public Timestamp getPostTime() {
		return postTime;
	}

	public void setPostTime(Timestamp postTime) {
		this.postTime = postTime;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", topicId=" + topicId + ", forumId=" + forumId + ", userId=" + userId + ", postText=" + postText + ", postAttach=" + Arrays.toString(postAttach) + ", postTime=" + postTime + "]";
	}

}
