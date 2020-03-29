import { Routes } from '@angular/router';
import { ListItemComponent } from './list-item/list-item.component';
import { ListPostComponent } from './list-post/list-post.component';
import { ListUserComponent } from './list-user/list-user.component';



export const ComponentsRoutes: Routes = [
	{
		path: '',
		children: [
			{
				path: 'list-item',
				component: ListItemComponent
			},
			{
				path: 'list-post',
				component: ListPostComponent
			},
			{
				path: 'list-user',
				component: ListUserComponent
			}
		]
	}
];
