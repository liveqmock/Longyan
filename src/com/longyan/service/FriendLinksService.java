package com.longyan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longyan.dao.impl.FriendLinksDaoImpl;
import com.longyan.entity.FriendLinks;

/**
 * 友情链接服务层
 * @author tracyqiu
 *
 */
@Service
public class FriendLinksService {

	@Autowired
	private FriendLinksDaoImpl friendLinksDaoImpl;

	public FriendLinksDaoImpl getFriendLinksDaoImpl() {
		return friendLinksDaoImpl;
	}

	public void setFriendLinksDaoImpl(FriendLinksDaoImpl friendLinksDaoImpl) {
		this.friendLinksDaoImpl = friendLinksDaoImpl;
	}
	
	/**
	 * 添加友情链接
	 * @param friendLinks
	 * @return
	 */
	public String addFriendLinks(FriendLinks friendLinks){
		return friendLinksDaoImpl.insert(friendLinks);
	}
	
	/**
	 * 修改友情链接信息
	 * @param friendLinks
	 * @return
	 */
	public String modifyFriendLinks(FriendLinks friendLinks){
		return friendLinksDaoImpl.update(friendLinks);
	}
	
	/**
	 * 根据友情链接ID删除友情链接
	 * @param friendLinks
	 * @return
	 */
	public String delFriendLinksById(FriendLinks friendLinks){
		return friendLinksDaoImpl.delete(friendLinks);
	}
	
	/**
	 * 批量删除友情链接
	 * @param ids
	 * @return
	 */
	public String delMoreFriendLinks(String ids){
		return friendLinksDaoImpl.deleteMore(ids);
	}
	
	/**
	 * 获取所有友情链接
	 * @return
	 */
	public List<FriendLinks> getAllFriendLinkss(){
		return friendLinksDaoImpl.findAll();
	}
	
	/**
	 * 根据名称查找友情链接信息
	 * @param name
	 * @return
	 */
	public List<FriendLinks> getFriendLinksesByName(String name){
		return friendLinksDaoImpl.findByName(name);
	}
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return
	 */
	public FriendLinks getFriendLinksById(Integer id){
		return friendLinksDaoImpl.findById(id);
	}
}
