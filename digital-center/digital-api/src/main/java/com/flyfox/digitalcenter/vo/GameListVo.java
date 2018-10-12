package com.flyfox.digitalcenter.vo;

import java.io.Serializable;

public class GameListVo implements Serializable {

	/**
	 *游戏名称
	 */
	private String gameName;
	/**
	 * 游戏图标
	 */
	private String gameIcon;
	/**
	 * 是否最新
	 */
	private Integer latest;
	/**
	 * 在玩总人数
	 */
	private Long sumCount;
	/**
	 * 几个礼包
	 */
	private String tip;//3個禮包
	
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGameIcon() {
		return gameIcon;
	}
	public void setGameIcon(String gameIcon) {
		this.gameIcon = gameIcon;
	}
	public Integer getLatest() {
		return latest;
	}
	public void setLatest(Integer latest) {
		this.latest = latest;
	}
	public Long getSumCount() {
		return sumCount;
	}
	public void setSumCount(Long sumCount) {
		this.sumCount = sumCount;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
	
}
