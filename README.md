Тематика: Пользователи могут создавать Заметки. Они, в свою очередь, имеют время `expiresAt` до которого они могут быть Закрепленными или Обычными, по истечению срока жизни становятся Истекшими.
Заметки выдаются в порядке: Закрепленные(`PINNED`) -> Обычные(`DEFAULT`) -> Истекшие(`EXPIRED`).
Заметки принадлежат пользователю, их можно создать, закрепить/открепить, изменить текст/время жизни или удалить.
Пользователь может просматривать только свои заметки. Админ может запросить все.
Пользователя можно создать, изменить его имя/фамилию. Удалить пользователя может только админ. Также может запросить всех пользователей.

Все эндпоинты описаны в `openapi.yaml` - в том числе защищенные "админские"

Логин и пароль для роли `ADMIN` - `admin:password`

Для выбора профиля передать в парамаетры VM `-Dspring.profiles.active=prod` или `-Dspring.profiles.active=dev` - prod на PostgreSQL, dev на H2

Имеется Scheduler, fixedRate=15sec для обновления истекших заметок
