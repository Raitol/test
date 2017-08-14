package com.shsxt.crm.service;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.shsxt.crm.dao.SaleChanceDao;
import com.shsxt.crm.dto.SaleChanceDto;
import com.shsxt.crm.dto.SaleChanceQuery;
import com.shsxt.crm.model.SaleChance;
import com.shsxt.crm.util.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleChanceService {

    @Autowired
    private SaleChanceDao saleChanceDao;

    private static Logger logger = LoggerFactory.getLogger(SaleChanceService.class);

    public Map<String, Object> selectForPage(SaleChanceQuery query) {
        Integer page = query.getPage();
        if (page == null) {
            page = 1;
        }
        Integer pageSize = query.getRows();
        if (pageSize == null) {
            pageSize = 10;
        }
        String sort = query.getSort();
        if (sort == null) {
            sort = "id.desc";
        }
        PageBounds pageBounds = new PageBounds(page, pageSize, Order.formString(sort));
        List<SaleChance> saleChances = saleChanceDao.selectForPage(query, pageBounds);
        PageList<SaleChance> result = (PageList<SaleChance>) saleChances;
        Paginator paginator = result.getPaginator();
        Map<String, Object> map = new HashMap<>();
        map.put("paginator", paginator);
        map.put("rows", result);
        map.put("total", paginator.getTotalCount());
        return map;

    }

    public void add(SaleChanceDto saleChanceDto, String userName) {
        checkParams(saleChanceDto.getCustomerId(), saleChanceDto.getCustomerName(), saleChanceDto.getCgjl());
        String assignMan = saleChanceDto.getAssignMan();
        int state = 0;
        Date assignTime = null;
        if (StringUtils.isNoneBlank(assignMan)) {
            state = 1;
            assignTime = new Date();
        }

        SaleChance saleChance = new SaleChance();
        BeanUtils.copyProperties(saleChanceDto, saleChance);
        saleChance.setAssignTime(assignTime);
        saleChance.setState(state);
        saleChance.setCreateMan(userName);
        int count = saleChanceDao.insert(saleChance);
        logger.debug("插入的记录数为：{}, 主键为：{}", count, saleChance.getId());

    }

    public void update(SaleChance saleChance) {
        Integer id = saleChance.getId();
        AssertUtil.intIsNotEmpty(id, "请选择要修改的记录");
        checkParams(saleChance.getCustomerId(), saleChance.getCustomerName(), saleChance.getCgjl());
        checkState(saleChance);
        saleChance.setUpdateDate(new Date());
        saleChanceDao.update(saleChance);
    }

    public void delete(String ids) {
        AssertUtil.isNotEmpty(ids, "请选择记录进行删除");
        saleChanceDao.delete(ids);
    }

    private void checkParams(Integer customerId, String customerName, Integer cgjl) {
        AssertUtil.intIsNotEmpty(customerId, "请选择客户");
        AssertUtil.isNotEmpty(customerName, "请选择客户");
        AssertUtil.intIsNotEmpty(cgjl, "请输入成功几率");
    }

    private void checkState(SaleChance saleChance) {
        SaleChance saleChanceFromDB = saleChanceDao.findById(saleChance.getId());
        AssertUtil.notNull(saleChanceFromDB, "该记录不存在，请重新选择");

        Integer state = saleChanceFromDB.getState();
        Date assignTime = null;
        String assignMan = saleChanceFromDB.getAssignMan();
        if (state == 0) {
            if (StringUtils.isNoneBlank(saleChance.getAssignMan())) {
                state = 1;
                assignTime = new Date();
            }
        } else {
            if (!assignMan.equals(saleChance.getAssignMan())) {
                if (StringUtils.isBlank(saleChance.getAssignMan())) {
                    state = 0;
                    assignTime = null;
                } else {
                    assignMan = saleChance.getAssignMan();
                    assignTime = new Date();
                }
            }
        }

        saleChance.setState(state);
        saleChance.setAssignTime(assignTime);
        saleChance.setAssignMan(assignMan);
    }

}
