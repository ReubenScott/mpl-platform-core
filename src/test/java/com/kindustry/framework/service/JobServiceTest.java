/**
 * 
 */
package com.kindustry.framework.service;

import org.junit.Test;

/**
 * 
 * @author kindustry
 *
 */
public class JobServiceTest {

  /**
   * Test method for {@link com.kindustry.framework.service.JobService#addJob(com.kindustry.framework.service.Worker)}.
   */
  @Test
  public void testAddJob() {
    JobService service = new JobService() {};
    JobHandler worker = new JobHandler() {

      @Override
      public void job() {

      }
    };
    service.addHandler(worker);
  }
}
