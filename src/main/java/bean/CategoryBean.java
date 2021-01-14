package bean;

import entity.Category;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CategoryBean {

    @PersistenceContext(unitName = "project_2")
    private EntityManager em;

    //work
    @Transactional(rollbackOn = Exception.class)
    public void addCategory(long position_id, String category_name) {
        Category select_category;
        Category insert_category = new Category();
        if (position_id != 0) {
            select_category = em.find(Category.class, position_id);
            em.createQuery("update Category c set c.left_key = c.left_key + 2, c.right_key = c.right_key + 2 where c.left_key > ?1").
                    setParameter(1, select_category.getLeft_key()).
                    executeUpdate();

            em.createQuery("update Category c set c.right_key = c.right_key + 2 where c.left_key <= ?1 and  c.right_key >= ?2").
                    setParameter(1, select_category.getLeft_key()).
                    setParameter(2, select_category.getRight_key()).
                    executeUpdate();

            insert_category.setLeft_key(select_category.getLeft_key() + 1);
            insert_category.setRight_key(select_category.getLeft_key() + 2);
            insert_category.setLevel(select_category.getLevel() + 1);
//            insert_category.setCategory(category_name);
        } else {
            select_category = em.createQuery("select c from Category c order by c.right_key desc", Category.class).
                    setMaxResults(1).
                    getSingleResult();

            insert_category.setLeft_key(select_category.getRight_key() + 1);
            insert_category.setRight_key(select_category.getRight_key() + 2);
            insert_category.setLevel(1);
        }
        insert_category.setCategory(category_name);
        em.persist(insert_category);
    }

    //work
    @Transactional(rollbackOn = Exception.class)
    public void removeCategory(long delete_id) {
        Category del_category = em.find(Category.class, delete_id);
        int left_remove = del_category.getLeft_key();
        int right_remove = del_category.getRight_key();
        int remove_diff = right_remove - left_remove + 1;

        em.createQuery("delete from Category c where c.left_key >= ?1 and c.right_key <= ?2").
                setParameter(1, left_remove).
                setParameter(2, right_remove).
                executeUpdate();

        em.createQuery("update Category c set c.left_key = c.left_key - ?1, c.right_key = c.right_key - ?1 where c.left_key > ?2 and c.right_key > ?3").
                setParameter(1, remove_diff).
                setParameter(2, left_remove).
                setParameter(3, right_remove).
                executeUpdate();

        em.createQuery("update Category c set c.right_key = c.right_key - ?1 where c.left_key < ?2 and c.right_key > ?3").
                setParameter(1, remove_diff).
                setParameter(2, left_remove).
                setParameter(3, right_remove).
                executeUpdate();
    }

    //work
    public void moveCategory(long from_pos, long to_pos) {
        Category from_category = em.find(Category.class, from_pos);
        if (to_pos != 0) {
            Category to_category = em.find(Category.class, to_pos);
            if ((from_category.getLeft_key() - to_category.getLeft_key()) > 0) {
                moveUpCategory(from_pos, to_pos);
            } else {
                moveDownCategory(from_pos, to_pos);
            }
        }
        if ((to_pos == 0) && (from_category.getLevel() != 1)) {
            moveCategoryToNewLevel(from_pos);
        }
    }

    //work (moveCategory)
    @Transactional(rollbackOn = Exception.class)
    private void moveCategoryToNewLevel(long from_pos) {
        Category from_category = em.find(Category.class, from_pos);
        int left = from_category.getLeft_key();
        int right = from_category.getRight_key();
        int move_sum = from_category.getRight_key() - from_category.getLeft_key() + 1;
        int moveLevelDifference = (from_category.getLevel() - (from_category.getLevel() - 1));
        //-------------------------------
//        List<Category> move_cat_tree;
//        move_cat_tree = em.createQuery("select c from Category c where c.left_key >= ?1 and c.right_key <= ?2 order by c.left_key", Category.class).
        em.createQuery("update Category c set c.left_key = - c.left_key, c.right_key = - c.right_key where c.left_key >= ?1 and c.right_key <= ?2 ").
                setParameter(1, left).
                setParameter(2, right).
                executeUpdate();
//                getResultList();
//        for (Category category : move_cat_tree) {
//            category.setLeft_key(-category.getLeft_key());
//            category.setRight_key(-category.getRight_key());
//            em.persist(category);
//        }
        //-------------------------------
//        move_cat_tree = em.createQuery("select c from Category c where c.left_key < ?1 and c.right_key > ?2 order by c.left_key", Category.class).
        em.createQuery("update Category c set c.right_key = c.right_key - ?1 where c.left_key < ?2 and c.right_key > ?3").
                setParameter(1, move_sum).
                setParameter(2, left).
                setParameter(3, right).
                executeUpdate();
//                getResultList();
//        for (Category category : move_cat_tree) {
//            category.setRight_key(category.getRight_key() - move_sum);
//            em.merge(category);
//        }
        //-------------------------------
//        move_cat_tree = em.createQuery("select c from Category c where c.left_key > ?1 order by c.left_key", Category.class).
        em.createQuery("update Category c set c.left_key = c.left_key - ?1, c.right_key = c.right_key - ?1 where c.left_key > ?2").
                setParameter(1, move_sum).
                setParameter(2, left).
                executeUpdate();
//                getResultList();
//        for (Category category : move_cat_tree) {
//            category.setLeft_key(category.getLeft_key() - move_sum);
//            category.setRight_key(category.getRight_key() - move_sum);
//            em.merge(category);
//        }
        //-------------------------------
        int max = em.createQuery("select max(c.right_key) from Category c ", Integer.class).getSingleResult();
//        move_cat_tree = em.createQuery("select c from Category c where c.left_key < 0 and c.right_key < 0", Category.class).
        em.createQuery("update Category c set c.left_key = ?1 + (- c.left_key - ?2) + 1, c.right_key = ?1 + (- c.right_key - ?2) + 1, c.level = c.level - ?3  where c.left_key < 0 and c.right_key < 0").
                setParameter(1, max).
                setParameter(2, left).
                setParameter(3, moveLevelDifference).
                executeUpdate();
//                getResultList();
//        for (Category category : move_cat_tree) {
//            category.setLevel((byte) (category.getLevel() - moveLevelDifference));
//            int left_k = -category.getLeft_key();
//            category.setLeft_key(max + ((left_k - left) + 1));
//            int right_k = -category.getRight_key();
//            category.setRight_key(max + ((right_k - left) + 1));
//            em.merge(category);
//        }
        //-------------------------------
    }

    //work (moveCategory)
    @Transactional(rollbackOn = Exception.class)
    private void moveUpCategory(long from_pos, long to_pos) {
        Category from_category = em.find(Category.class, from_pos);
        Category to_category = em.find(Category.class, to_pos);

        int move_sum = from_category.getRight_key() - from_category.getLeft_key() + 1;
        int move_position = from_category.getLeft_key() - to_category.getLeft_key() - 1;
        int moveLevelDifference = getMoveLevelDifference(from_category.getLevel(), to_category.getLevel());

        em.createQuery("update Category c set c.right_key = - c.right_key, c.left_key = - c.left_key where c.left_key >= ?1 and c.right_key <= ?2").
                setParameter(1, from_category.getLeft_key()).
                setParameter(2, from_category.getRight_key()).
                executeUpdate();

        em.createQuery("update Category c set c.right_key = c.right_key - ?1 where c.left_key < ?2 and  c.right_key > ?3").
                setParameter(1, move_sum).
                setParameter(2, from_category.getLeft_key()).
                setParameter(3, from_category.getRight_key()).
                executeUpdate();

        em.createQuery("update Category c set c.right_key = c.right_key - ?1, c.left_key = c.left_key - ?1 where c.left_key > ?2").
                setParameter(1, move_sum).
                setParameter(2, from_category.getLeft_key()).
                executeUpdate();

        em.createQuery("update Category c set c.right_key = c.right_key + ?1, c.left_key = c.left_key + ?1 where c.left_key > ?2").
                setParameter(1, move_sum).
                setParameter(2, to_category.getLeft_key()).
                executeUpdate();

        em.createQuery("update Category c set c.right_key = c.right_key + ?1 where c.left_key<= ?2 and  c.right_key >= ?3").
                setParameter(1, move_sum).
                setParameter(2, to_category.getLeft_key()).
                setParameter(3, to_category.getRight_key()).
                executeUpdate();

        em.createQuery("update Category c set c.right_key = - c.right_key - ?1, c.left_key = - c.left_key - ?1, c.level = c.level + ?2 where c.left_key < 0 and c.right_key < 0").
                setParameter(1, move_position).
                setParameter(2, moveLevelDifference).
                executeUpdate();
    }

    //work (moveCategory)
    @Transactional(rollbackOn = Exception.class)
    private void moveDownCategory(long from_pos, long to_pos) {
        Category from_category = em.find(Category.class, from_pos);
        Category to_category = em.find(Category.class, to_pos);


        int move_sum = from_category.getRight_key() - from_category.getLeft_key() + 1;
        int move_position = to_category.getLeft_key() - from_category.getLeft_key() - move_sum + 1;
        int moveLevelDifference = getMoveLevelDifference(from_category.getLevel(), to_category.getLevel());

        em.createQuery("update Category c set c.right_key = - c.right_key, c.left_key = - c.left_key where  c.left_key >= ?1 and c.right_key <= ?2").
                setParameter(1, from_category.getLeft_key()).
                setParameter(2, from_category.getRight_key()).
                executeUpdate();

        em.createQuery("update Category c set c.right_key = c.right_key - ?1 where c.right_key > ?2 and  c.left_key < ?3").
                setParameter(1, move_sum).
                setParameter(2, from_category.getRight_key()).
                setParameter(3, from_category.getLeft_key()).
                executeUpdate();

        em.createQuery("update Category c set c.right_key = c.right_key - ?1, c.left_key = c.left_key - ?1 where c.left_key > ?2 and c.left_key <= ?3").
                setParameter(1, move_sum).
                setParameter(2, from_category.getLeft_key()).
                setParameter(3, to_category.getLeft_key()).
                executeUpdate();

        em.createQuery("update Category c set c.right_key = c.right_key + ?1 where  c.left_key <= (?2 - ?1) and c.right_key >= (?3 - ?1)").
                setParameter(1, move_sum).
                setParameter(2, to_category.getLeft_key()).
                setParameter(3, to_category.getRight_key()).
                executeUpdate();

        em.createQuery("update Category c set c.right_key = - c.right_key + ?1, c.left_key = - c.left_key + ?1, c.level = c.level + ?2 where  c.left_key < 0 and c.right_key < 0").
                setParameter(1, move_position).
                setParameter(2, moveLevelDifference).
                executeUpdate();
    }

    //work (moveCategory)
    private int getMoveLevelDifference(int level_from, int level_to) {
        if (level_from > level_to) {
            return -(level_from - level_to - 1);
        } else {
            return (level_to - level_from + 1);
        }
    }

    //work
    public Category getCategory(long category_id) {
        return em.find(Category.class, category_id);
    }

    //work
    public List<Category> getAllCategories() {
        return em.createQuery("select c from Category c order by c.left_key", Category.class).
                getResultList();
    }

    //work
    public List<Category> getLastPointOfEachBranch() {
        List<Category> categoryList = getAllCategories();
        List<Category> LastPointOfEachBranch = new ArrayList<>();
        for (int i = 0; i < categoryList.size() - 1; i++) {
            //***
            if (categoryList.get(i).getLevel() > categoryList.get(i + 1).getLevel()) {
                LastPointOfEachBranch.add(categoryList.get(i));
                if ((i + 2) == categoryList.size()) {
                    LastPointOfEachBranch.add(categoryList.get(i + 1));
                }
            }
            //***
            if (categoryList.get(i).getLevel() == categoryList.get(i + 1).getLevel()) {
                LastPointOfEachBranch.add(categoryList.get(i));
                if ((i + 2) == categoryList.size()) {
                    LastPointOfEachBranch.add(categoryList.get(i + 1));
                }
            }
            //***
            if ((categoryList.get(i).getLevel() < categoryList.get(i + 1).getLevel()) && ((i + 2) == categoryList.size())) {
                LastPointOfEachBranch.add(categoryList.get(i + 1));
            }
        }
        return LastPointOfEachBranch;
    }

    //work
    public List<Category> getFullBranchOnTheLastCategoryPoint(Category category) {
        return em.createQuery("select c from Category  c where  c.left_key <= ?1 and c.right_key >= ?2 order by c.left_key", Category.class).
                setParameter(1, category.getLeft_key()).
                setParameter(2, category.getRight_key()).
                getResultList();
    }
}
