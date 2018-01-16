package cn.nukkit.network.protocol;

public class BookEditPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.BOOK_EDIT_PACKET;
    public int action;
    public byte bookSlot;
    public byte pageIndex;
    public byte pageIndexSwap;
    public String textA;
    public String textB;


    public static final byte REPLACE_PAGE = 0;
    public static final byte ADD_PAGE = 1;
    public static final byte DELETE_PAGE = 2;
    public static final byte SWAP_PAGES = 3;
    public static final byte FINALIZE = 4;

    @Override
    public byte pid() {
        return ProtocolInfo.BOOK_EDIT_PACKET;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        switch (action) {
            case REPLACE_PAGE:
                putByte(pageIndex);
                putString(textA);
                putString(textB);
                break;
            case ADD_PAGE:
                putByte(pageIndex);
                putString(textA);
                putString(textB);
                break;
            case DELETE_PAGE:
                putByte(pageIndex);
                break;
            case SWAP_PAGES:
                putByte(pageIndex);
                putByte(pageIndexSwap);
                break;
            case FINALIZE:
                putString(textA);
                putString(textB);
                break;

        }
    }


}
