package com.longyan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longyan.dao.impl.ContactDaoImpl;
import com.longyan.entity.Contact;

/**
 * 客服服务层
 * @author tracyqiu
 *
 */
@Service
public class ContactService {

	@Autowired
	private ContactDaoImpl contactDaoImpl;

	public ContactDaoImpl getContactDaoImpl() {
		return contactDaoImpl;
	}

	public void setContactDaoImpl(ContactDaoImpl contactDaoImpl) {
		this.contactDaoImpl = contactDaoImpl;
	}
	
	/**
	 * 添加客服
	 * @param contact
	 * @return
	 */
	public String addContact(Contact contact){
		return contactDaoImpl.insert(contact);
	}
	
	/**
	 * 修改客服信息
	 * @param contact
	 * @return
	 */
	public String modifyContact(Contact contact){
		return contactDaoImpl.update(contact);
	}
	
	/**
	 * 根据客服ID删除客服
	 * @param contact
	 * @return
	 */
	public String delContactById(Contact contact){
		return contactDaoImpl.delete(contact);
	}
	
	/**
	 * 批量删除客服
	 * @param ids
	 * @return
	 */
	public String delMoreContact(String ids){
		return contactDaoImpl.deleteMore(ids);
	}
	
	/**
	 * 获取所有客服
	 * @return
	 */
	public List<Contact> getAllContacts(){
		return contactDaoImpl.findAll();
	}
	
	/**
	 * 根据名称查找客服信息
	 * @param name
	 * @return
	 */
	public List<Contact> getContactsByName(String name){
		return contactDaoImpl.findByName(name);
	}
	
	/**
	 * 根据ID查找客服
	 * @param id
	 * @return
	 */
	public Contact getContactById(Integer id){
		return contactDaoImpl.findById(id);
	}
}
