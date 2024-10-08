package qz.userdictionary.Service;

import android.content.Context;
import android.provider.UserDictionary;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import qz.userdictionary.Model.TextItems;
import qz.userdictionary.Model.UserDictionaryHelper;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * AddItemOnService
 */
public class AddItemOnService extends Worker {
  public AddItemOnService(@NonNull Context ctx, @NonNull WorkerParameters workerParams) {
    super(ctx, workerParams);

  }

  @NonNull
  @Override
  public Result doWork() {
    String key1 = getInputData().getString("key1");
    String key2 = getInputData().getString("key2");
    new UserDictionaryHelper(getApplicationContext())
        .add(
            new TextItems(
                key2,
                key1,
                "250",
                String.valueOf(UserDictionary.Words.LOCALE_TYPE_ALL)));
    showSuccessNotification("QZ UserDic", String.format("%s : %s", key1, key2));
    return Result.success();
  }

  private void showSuccessNotification(String title, String message) {
    int notificationId = 1;
    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "channel_id") // Anda
        .setSmallIcon(android.R.drawable.ic_notification_overlay)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
    notificationManager.notify(notificationId, builder.build());
  }
}
