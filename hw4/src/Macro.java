import model.Filters.Filter;

/**
 * Represents the command interface.
 */
public interface Macro {

  /**
   * Takes in a filter object and executes it
   */
  public void executesFilterMacro(Filter filter);

}
