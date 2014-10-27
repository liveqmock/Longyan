package com.longyan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longyan.dao.impl.ColumnDaoImpl;
import com.longyan.entity.Column;

/**
 * 栏目服务层封装
 * @author tracyqiu
 *
 */
@Service
public class ColumnService {

	@Autowired
	private ColumnDaoImpl columnDaoImpl;

	public ColumnDaoImpl getColumnDaoImpl() {
		return columnDaoImpl;
	}

	public void setColumnDaoImpl(ColumnDaoImpl columnDaoImpl) {
		this.columnDaoImpl = columnDaoImpl;
	}
	
	/**
	 * 添加栏目
	 * @param column
	 * @return
	 */
	public String addColumn(Column column){
		return columnDaoImpl.insert(column);
	}
	
	/**
	 * 删除栏目
	 * @param column
	 * @return
	 */
	public String delColumn(Column column){
		return columnDaoImpl.delete(column);
	}
	
	/**
	 * 更新栏目信息
	 * @param column
	 * @return
	 */
	public String modifyColumn(Column column){
		return columnDaoImpl.update(column);
	}
	
	/**
	 * 通过ID获取栏目
	 * @param id
	 * @return
	 */
	public Column getColumnById(Integer id){
		return columnDaoImpl.findById(id);
	}
	
	/**
	 * 通过名称查找栏目
	 * @param name
	 * @return
	 */
	public List<Column> getColumnsByName(String name){
		return columnDaoImpl.findByName(name);
	}
	
	/**
	 * 获取某个站点下的所有栏目
	 * @param site_id
	 * @return
	 */
	public List<Column> getColumnsBySiteId(Integer site_id){
		return columnDaoImpl.findBySiteId(site_id);
	}
	
	/**
	 * 批量删除栏目
	 * @param ids
	 * @return
	 */
	public String delMoreColumn(String ids){
		return columnDaoImpl.deleteMore(ids);
	}
	
	/**
	 * 取得所有栏目
	 * @return
	 */
	public List<Column> getAllColumns(){
		return columnDaoImpl.findAll();
	}
}
