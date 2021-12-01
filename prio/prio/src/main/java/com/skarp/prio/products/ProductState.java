package com.skarp.prio.products;
/**
 * The {@code ProductState} enum stores the different states that a product could be.
 * ???This information is stored in the {@link com.skarp.prio.products.Product Product}'s object.
 *
 *  These are the possible product states:
 *
 *  <p>
 *  - DEFECTIVE
 *  <p>
 *  - IN_REPAIR
 *  <p>
 *  - IN_WRITEOFF
 *  <p>
 *  - WRITTEN_OFF
 *  <p>
 *  - REPAIRED
 *  <p>
 *
 * This is an example of how we can set the product state of an already existing
 * {@link com.skarp.prio.products.Product Product}'s object:
 *
 * <blockquote><pre>
 *      Product product = new Product("productId");
 *      product.ProductState(ProductState.DEFECTIVE);
 * </pre></blockquote>
 *
 * @author Team-Skarp
 * @see com.skarp.prio.products
 * @since ???
 */
public enum ProductState {
    /** ??? */
    DEFECTIVE,
    IN_REPAIR,
    IN_WRITEOFF,
    WRITTEN_OFF,
    REPAIRED
}
