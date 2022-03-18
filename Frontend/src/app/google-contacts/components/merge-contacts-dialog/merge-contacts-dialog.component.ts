import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MergeService } from '../../services/merge.service';

@Component({
  selector: 'merge-contacts-dialog',
  templateUrl: './merge-contacts-dialog.component.html',
  styleUrls: ['./merge-contacts-dialog.component.scss'],
})
export class MergeContactsDialogComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: number[],
    private mergeService: MergeService
  ) {}

  ngOnInit(): void {}

  merge() {
    this.mergeService.mergeContacts(this.data).subscribe((data) => {
      window.location.href = 'contacts/person/' + data + '/edit';
    });
  }
}
