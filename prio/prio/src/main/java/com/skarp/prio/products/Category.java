package com.skarp.prio.products;
/**
 * The {@code Category} enum stores the different electronic devices that a product could be.
 * This information is stored in the {@link com.skarp.prio.products.Product Product}'s object.
 *
 *  These are the possible values:
 *
 *  <p>
 *  - IPHONE
 *  <p>
 *  - MACBOOK
 *  <p>
 *  - IPAD
 *  <p>
 *  - LAPTOP
 *  <p>
 *  - SMARTPHONE
 *  <p>
 *  - TABLET
 *  <p>
 *
 * This is an example of how we can set the  of electronic device of an already existing
 * {@link com.skarp.prio.products.Product Product}'s object:
 *
 * <blockquote><pre>
 *      Product product = new Product("productId");
 *      product.setCategory(Category.IPHONE);
 * </pre></blockquote>
 *
 * @author Team-Skarp
 * @see com.skarp.prio.products
 * @since ???
 */
public enum Category {
    /** ??? */
    IPHONE,
    MACBOOK,
    IPAD,
    LAPTOP,
    SMARTPHONE,
    TABLET
}
