package cn.lizhiyu.closeeye.model;

public class MovieDetailItemModel
{
    public int type;

    public MovieItemModel movieItemModel;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MovieItemModel getMovieItemModel() {
        return movieItemModel;
    }

    public void setMovieItemModel(MovieItemModel movieItemModel) {
        this.movieItemModel = movieItemModel;
    }
}
