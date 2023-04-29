package model;

public class ProposalListDto {
    private String name;
    private String imagePath;

    public ProposalListDto(String name, String imagePath){
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
    public String getName() {
        return name;
    }
}
