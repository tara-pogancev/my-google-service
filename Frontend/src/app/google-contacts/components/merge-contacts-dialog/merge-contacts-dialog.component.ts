import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'merge-contacts-dialog',
  templateUrl: './merge-contacts-dialog.component.html',
  styleUrls: ['./merge-contacts-dialog.component.scss'],
})
export class MergeContactsDialogComponent implements OnInit {
  constructor(@Inject(MAT_DIALOG_DATA) public data: number[]) {}

  ngOnInit(): void {}

  //TODO merge selected
}
