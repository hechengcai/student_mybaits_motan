package com.student.room.service.model;
import javax.persistence.*;

@Table(name = "columns")
public class ColumnsInfo {
    /**
     * 模块id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 上级id
     */
    private Integer parentid;

    /**
     * 模块名称
     */
    private String title;

    /**
     * 排序
     */
    private Integer ord;

    /**
     * 模块处理url
     */
    private String url;

    /**
     * 状态：0禁用，1启用
     */
    private Byte status;

    /**
     * 创建人id
     */
    private Integer creator;

    /**
     * 更新者id
     */
    private Integer editor;

    /**
     * 添加时间
     */
    @Column(name = "create_time")
    private Integer createTime;

    /**
     * 更新时间
     */
    @Column(name = "edit_time")
    private Integer editTime;

    /**
     * 获取模块id
     *
     * @return id - 模块id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置模块id
     *
     * @param id 模块id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取上级id
     *
     * @return parentid - 上级id
     */
    public Integer getParentid() {
        return parentid;
    }

    /**
     * 设置上级id
     *
     * @param parentid 上级id
     */
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    /**
     * 获取模块名称
     *
     * @return title - 模块名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置模块名称
     *
     * @param title 模块名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取排序
     *
     * @return ord - 排序
     */
    public Integer getOrd() {
        return ord;
    }

    /**
     * 设置排序
     *
     * @param ord 排序
     */
    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    /**
     * 获取模块处理url
     *
     * @return url - 模块处理url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置模块处理url
     *
     * @param url 模块处理url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取状态：0禁用，1启用
     *
     * @return status - 状态：0禁用，1启用
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态：0禁用，1启用
     *
     * @param status 状态：0禁用，1启用
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取创建人id
     *
     * @return creator - 创建人id
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * 设置创建人id
     *
     * @param creator 创建人id
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * 获取更新者id
     *
     * @return editor - 更新者id
     */
    public Integer getEditor() {
        return editor;
    }

    /**
     * 设置更新者id
     *
     * @param editor 更新者id
     */
    public void setEditor(Integer editor) {
        this.editor = editor;
    }

    /**
     * 获取添加时间
     *
     * @return create_time - 添加时间
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * 设置添加时间
     *
     * @param createTime 添加时间
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return edit_time - 更新时间
     */
    public Integer getEditTime() {
        return editTime;
    }

    /**
     * 设置更新时间
     *
     * @param editTime 更新时间
     */
    public void setEditTime(Integer editTime) {
        this.editTime = editTime;
    }
}