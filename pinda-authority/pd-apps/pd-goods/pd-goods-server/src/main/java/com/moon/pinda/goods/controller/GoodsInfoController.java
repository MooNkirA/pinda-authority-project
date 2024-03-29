package com.moon.pinda.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moon.pinda.base.BaseController;
import com.moon.pinda.base.R;
import com.moon.pinda.base.entity.SuperEntity;
import com.moon.pinda.database.mybatis.conditions.Wraps;
import com.moon.pinda.database.mybatis.conditions.query.LbqWrapper;
import com.moon.pinda.dozer.DozerUtils;
import com.moon.pinda.goods.dto.GoodsInfoPageDTO;
import com.moon.pinda.goods.dto.GoodsInfoSaveDTO;
import com.moon.pinda.goods.dto.GoodsInfoUpdateDTO;
import com.moon.pinda.goods.entity.GoodsInfo;
import com.moon.pinda.goods.service.GoodsInfoService;
import com.moon.pinda.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品控制层
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-28 10:53
 * @description
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/goodsInfo")
@Api(value = "GoodsInfo", tags = "商品信息")
public class GoodsInfoController extends BaseController {

    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private GoodsInfoService goodsInfoService;

    /**
     * 分页查询商品信息
     *
     * @param data 分页查询对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询商品信息", notes = "分页查询商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @GetMapping("/page")
    @SysLog("分页查询商品信息")
    public R<IPage<GoodsInfo>> page(GoodsInfoPageDTO data) {
        Page<GoodsInfo> page = getPage();
        LbqWrapper<GoodsInfo> wrapper = Wraps.lbQ();

        wrapper.like(GoodsInfo::getName, data.getName())
                .like(GoodsInfo::getCode, data.getCode())
                .eq(GoodsInfo::getBarCode, data.getBarCode())
                .geHeader(GoodsInfo::getCreateTime, data.getStartCreateTime())
                .leFooter(GoodsInfo::getCreateTime, data.getEndCreateTime())
                .orderByDesc(GoodsInfo::getCreateTime);

        goodsInfoService.page(page, wrapper);
        return success(page);
    }

    @ApiOperation(value = "查询商品信息", notes = "查询商品信息")
    @GetMapping("/list")
    @SysLog("查询商品信息")
    public R<List<GoodsInfo>> list(GoodsInfoPageDTO data) {

        LbqWrapper<GoodsInfo> wrapper = Wraps.lbQ();

        wrapper.like(GoodsInfo::getName, data.getName())
                .like(GoodsInfo::getCode, data.getCode())
                .eq(GoodsInfo::getBarCode, data.getBarCode())
                .geHeader(GoodsInfo::getCreateTime, data.getStartCreateTime())
                .leFooter(GoodsInfo::getCreateTime, data.getEndCreateTime())
                .orderByDesc(GoodsInfo::getCreateTime);

        return success(goodsInfoService.list(wrapper));
    }

    /**
     * 查询商品信息
     *
     * @param id 主键id
     * @return 查询结果
     */
    @ApiOperation(value = "查询商品信息", notes = "查询商品信息")
    @GetMapping("/{id}")
    @SysLog("查询商品信息")
    public R<GoodsInfo> get(@PathVariable Long id) {
        return success(goodsInfoService.getById(id));
    }

    /**
     * 新增商品信息
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增商品信息", notes = "新增商品信息不为空的字段")
    @PostMapping
    @SysLog("新增商品信息")
    public R<GoodsInfo> save(@RequestBody @Validated GoodsInfoSaveDTO data) {
        GoodsInfo GoodsInfo = dozerUtils.map(data, GoodsInfo.class);
        goodsInfoService.save(GoodsInfo);
        return success(GoodsInfo);
    }

    /**
     * 修改商品信息
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @ApiOperation(value = "修改商品信息", notes = "修改商品信息不为空的字段")
    @PutMapping
    @SysLog("修改商品信息")
    public R<GoodsInfo> update(@RequestBody @Validated(SuperEntity.Update.class) GoodsInfoUpdateDTO data) {
        GoodsInfo GoodsInfo = dozerUtils.map(data, GoodsInfo.class);
        goodsInfoService.updateById(GoodsInfo);
        return success(GoodsInfo);
    }

    /**
     * 删除商品信息
     *
     * @param ids 主键id
     * @return 删除结果
     */
    @ApiOperation(value = "删除商品信息", notes = "根据id物理删除商品信息")
    @SysLog("删除商品信息")
    @DeleteMapping
    public R<Boolean> delete(@RequestParam("ids[]") List<Long> ids) {
        goodsInfoService.removeByIds(ids);
        return success();
    }
}
