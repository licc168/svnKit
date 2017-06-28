import com.troila.svn.conf.SvnConfig;
import com.troila.svn.factory.TroilaSvn;
import com.troila.svn.inf.ISvn;
import com.troila.svn.tools.FileUtil;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/6/28 13:09
 * @see
 */
public class SvnKitTest {
  String                     account     = "lf";
  String                     password    = "171314";
  String                     svnPath        = "https://192.168.102.166/svn/JThinkTank/branches/JTTCenter1.2.0";
  String   svnUrl = "https://192.168.102.166/";
  ISvn svn;
  SVNRepository repository;
  String                     svnLocalPath = "E:/svnkit/svn_70/JTTCenter1.2.0";
  protected SVNClientManager clientManager;                                                                // svn客户操作服务

  @Before
  public void init() throws Exception {
    // 初始化实例
    TroilaSvn ts = new TroilaSvn(account, password, svnPath);
    // 获得操作对象
    this.svn = ts.execute(SvnConfig.log);
    // 得到版本库信息
    repository = svn.createSVNRepository();
    // 得到基础操作对象
    clientManager = svn.createSVNClientManager();

  }

  @Test
  public void svnKit() throws SVNException {
    long startRevision = 0;
    long endRevision = -1;// 表示最后一个版本
    final Date end = new Date();
    Date begin = Date.from(Instant.now().minus(3, ChronoUnit.HOURS));
    final List<String> history = new ArrayList<String>();
    repository.log(new String[] { "" }, startRevision, endRevision, true, true, new ISVNLogEntryHandler() {
      @Override
      public void handleLogEntry(SVNLogEntry svnlogentry) throws SVNException {
        // 依据提交时间进行过滤
        if (svnlogentry.getDate().after(begin) && svnlogentry.getDate().before(end)) {
          fillResult(svnlogentry);
        }
      }
      public void fillResult(SVNLogEntry svnlogentry) {
        // getChangedPaths为提交的历史记录MAP key为文件名，value为文件详情
        history.addAll(svnlogentry.getChangedPaths().keySet());
      }
    });
    history.stream().forEach(path->{
      String localPath =  path.substring(path.indexOf('/', 15), path.length());
      localPath =  svnLocalPath+localPath;
      try {
        SVNProperties fileProperties = new SVNProperties();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String url = svnUrl+  repository.getFullPath(path);
        try {
          long l = repository.getFile(path, -1, fileProperties, outputStream);
        }catch (Exception e){
          //路径没找到则删除本地路径
          FileUtil.deleteFiles(localPath);
        }
        //获取svn数据并复制到本地
        FileUtil.copyFile(outputStream,localPath,true);
      } catch (Exception e) {
        e.printStackTrace();
      }




    });
  }
}
