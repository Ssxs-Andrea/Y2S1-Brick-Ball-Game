package block;

import java.io.Serializable;
/**
 * The {@code BlockSerializable} class represents a serializable version of a game block.
 * It stores essential information about the block's position and type.
 *
 */
public class BlockSerializable implements Serializable {
    /**
     * The row position of the block in the game grid.
     */
    public final int row;
    /**
     * The column position of the block in the game grid.
     */
    public final int column;
    /**
     * The type of the block, representing its characteristics.
     */
    public final int type;
    /**
     * Constructs a BlockSerializable object with the specified row, column, and type.
     *
     * @param row   The row position of the block.
     * @param column The column position of the block.
     * @param type   The type of the block.
     */
    public BlockSerializable(int row , int column , int type) {
        this.row = row;
        this.column = column;
        this.type = type;
    }
}
