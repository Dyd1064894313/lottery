package top.duanyd.lottery.entity;

/**
 * Created by Administrator on 2018/8/23.
 */
public class MenuEntity {
    private Long id;
    private String code;
    private String name;
    private String prentCode;
    private String prentName;
    private String url;
    private String img;
    private Boolean selected = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrentCode() {
        return prentCode;
    }

    public void setPrentCode(String prentCode) {
        this.prentCode = prentCode;
    }

    public String getPrentName() {
        return prentName;
    }

    public void setPrentName(String prentName) {
        this.prentName = prentName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
