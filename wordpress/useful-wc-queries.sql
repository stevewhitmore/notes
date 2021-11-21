--Empty trash
DELETE p
FROM wp_posts p
LEFT OUTER JOIN wp_postmeta pm ON pm.post_id = p.ID
WHERE post_status = 'trash';

--Update with trash status
UPDATE wp_posts p 
JOIN wp_postmeta pm ON pm.post_id = p.ID
SET p.post_status = 'trash'
WHERE pm.meta_key = '_sku'
AND pm.meta_value IN ();

-- Verify product is present
SELECT *
FROM wp_posts p
JOIN wp_postmeta pm ON pm.post_id = p.ID
WHERE pm.meta_key = '_sku'
AND pm.meta_value = '';

-- Get all product eIDs
SELECT DISTINCT pm.meta_value
FROM wp_postmeta pm
JOIN wp_posts p ON p.ID = pm.post_id
WHERE pm.meta_key = '_sku'
AND p.post_status = 'publish';

-- Find dupicates
SELECT pm.meta_value, p.id, COUNT(*)
FROM wp_postmeta pm
JOIN wp_posts p ON p.ID = pm.post_id
WHERE pm.meta_key = '_sku'
AND p.post_status = 'publish'
GROUP BY pm.meta_value
HAVING COUNT(*) > 1;
