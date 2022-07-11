package http;

public class Paginator {

    private final int limit, offset;

    public Paginator(int page, int itemsPerPage) throws InvalidRequestException {
        if(page < 1 || itemsPerPage < 1)
            throw new InvalidRequestException(500);

        this.limit = itemsPerPage;
        if(page == 1)
            this.offset = 0;
        else
            this.offset = (page - 1) * itemsPerPage;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getPages(int size) {
        int ap = (size%limit==0) ? 0 : 1;
        return (size/limit)+ap;
    }
}
